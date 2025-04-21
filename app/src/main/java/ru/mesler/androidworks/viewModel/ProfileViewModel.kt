package ru.mesler.androidworks.viewModel

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import org.koin.java.KoinJavaComponent
import ru.mesler.androidworks.domain.model.Profile
import ru.mesler.androidworks.domain.repository.IProfileRepository
import ru.mesler.androidworks.state.ProfileState
import ru.mesler.androidworks.utils.launchLoadingAndError
import java.io.File
import java.util.Date

class ProfileViewModel(
    private val repository: IProfileRepository,
    private val navigation: NavHostController
) : ViewModel() {

    private val mutableState = MutableProfileState()
    val viewState = mutableState as ProfileState
    private val context: Context by KoinJavaComponent.inject(Context::class.java)

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.isLoading = it }
        ) {
            repository.getProfile()?.let { profile ->
                updateState(profile)
            }
        }
    }

    fun onEditProfileClicked() {
        navigation.navigate("edit_profile")
    }

    fun onDocumentClick() {
        if (viewState.resumeUrl.isBlank()) {
            mutableState.error = "URL резюме не указан"
            return
        }
        try {
            downloadDocument(context, viewState.resumeUrl)
        } catch (e: Exception) {
            mutableState.error = "Ошибка при загрузке документа: ${e.message}"
        }
    }

    private fun downloadDocument(context: Context, url: String) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(url.toUri())

        val fileExtension = url.substringAfterLast('.', "pdf")
        val filename = "resume_${Date().time}.$fileExtension"

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setTitle("Загрузка резюме")
        request.setDescription("Загрузка файла резюме")

        val downloadId = downloadManager.enqueue(request)
        val onComplete: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == downloadId) {
                    try {
                        onDownloadComplete(context, filename)
                    } catch (e: Exception) {
                        mutableState.error = "Ошибка при открытии документа: ${e.message}"
                    } finally {
                        context.unregisterReceiver(this)
                    }
                }
            }
        }

        ContextCompat.registerReceiver(
            context,
            onComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    private fun onDownloadComplete(context: Context, filename: String) {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            filename
        )

        if (!file.exists()) {
            throw IllegalStateException("Файл не найден")
        }

        val mimeType = getMimeType(filename)
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        val resolveInfo = context.packageManager.queryIntentActivities(intent, 0)
        if (resolveInfo.isNotEmpty()) {
            context.startActivity(intent)
        } else {
            val fallbackIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "*/*")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            if (fallbackIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(fallbackIntent)
            } else {
                throw IllegalStateException("Нет приложения для открытия файла")
            }
        }
    }

    private fun getMimeType(filename: String): String {
        return when (filename.substringAfterLast('.').lowercase()) {
            "pdf" -> "application/pdf"
            "doc" -> "application/msword"
            "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            "txt" -> "text/plain"
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            else -> "application/octet-stream"
        }
    }

    fun onSaveProfile(profile: Profile) {
        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.isLoading = it }
        ) {
            val profileSaved = repository.setProfile(profile = profile)
            updateState(profileSaved)
            navigation.popBackStack()
        }
    }

    private fun updateState(profile: Profile) {
        mutableState.fio = profile.fio
        mutableState.avatarUri = profile.avatarUri
        mutableState.resumeUrl = profile.resumeUrl
        mutableState.position = profile.position
        mutableState.email = profile.email
    }

    class MutableProfileState : ProfileState {
        override var fio: String by mutableStateOf("")
        override var avatarUri: String by mutableStateOf("")
        override var resumeUrl: String by mutableStateOf("")
        override var position: String by mutableStateOf("")
        override var email: String by mutableStateOf("")
        override var isLoading: Boolean by mutableStateOf(false)
        override var error: String? by mutableStateOf(null)
    }
}