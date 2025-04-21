package ru.mesler.androidworks.content

import android.Manifest
import android.R
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.mesler.androidworks.domain.model.Profile
import ru.mesler.androidworks.viewModel.ProfileViewModel
import java.io.File
import java.io.FileOutputStream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navigation: NavHostController) {
    val viewModel = koinViewModel<ProfileViewModel> { parametersOf(navigation) }
    val state = viewModel.viewState
    val context = LocalContext.current

    val storagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    var hasStoragePermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                storagePermission
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val storagePermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasStoragePermission = isGranted
            if (!isGranted) {
                navigation.popBackStack()
            }
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasCameraPermission = isGranted
        }

    if (!hasStoragePermission) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Доступ к файловому хранилищу") },
            text = { Text(text = "Для редактирования профиля требуется доступ к файловому хранилищу") },
            confirmButton = {
                TextButton(onClick = {
                    storagePermissionLauncher.launch(storagePermission)
                }) {
                    Text("Разрешить")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    navigation.popBackStack()
                }) {
                    Text("Отмена")
                }
            }
        )
        return
    }

    if (!hasCameraPermission) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Доступ к камере") },
            text = { Text(text = "Для использования камеры требуется разрешение") },
            confirmButton = {
                TextButton(onClick = {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }) {
                    Text("Разрешить")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    navigation.popBackStack()
                }) {
                    Text("Отмена")
                }
            }
        )
        return
    }

    var fio by remember { mutableStateOf(state.fio) }
    var position by remember { mutableStateOf(state.position) }
    var email by remember { mutableStateOf(state.email) }
    var avatarUri by remember { mutableStateOf(state.avatarUri) }
    var resumeUrl by remember { mutableStateOf(state.resumeUrl) }

    var showImageSourceDialog by remember { mutableStateOf(false) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                val inputStream = context.contentResolver.openInputStream(it)
                val file = File(context.cacheDir, "avatar_${System.currentTimeMillis()}.jpg")
                FileOutputStream(file).use { outputStream ->
                    inputStream?.copyTo(outputStream)
                }
                avatarUri = file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempImageUri != null) {
            try {
                val inputStream = context.contentResolver.openInputStream(tempImageUri!!)
                val file = File(context.cacheDir, "avatar_${System.currentTimeMillis()}.jpg")
                FileOutputStream(file).use { outputStream ->
                    inputStream?.copyTo(outputStream)
                }
                avatarUri = file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { showImageSourceDialog = false },
            title = { Text(text = "Выбор источника") },
            text = { Text(text = "Выберите источник") },
            confirmButton = {
                TextButton(onClick = {
                    showImageSourceDialog = false
                    galleryLauncher.launch(arrayOf("image/*"))
                }) {
                    Text("Галерея")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showImageSourceDialog = false
                    val file = File(context.cacheDir, "temp_camera_${System.currentTimeMillis()}.jpg")
                    tempImageUri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        file
                    )
                    cameraLauncher.launch(tempImageUri!!)
                }) {
                    Text("Камера")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Редактирование профиля") },
                navigationIcon = {
                    IconButton(onClick = { navigation.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            viewModel.onSaveProfile(
                                Profile(
                                    fio = fio,
                                    position = position,
                                    email = email,
                                    avatarUri = avatarUri,
                                    resumeUrl = resumeUrl
                                )
                            )
                        }
                    ) {
                        Text("Сохранить")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                state.error?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                AsyncImage(
                    model = avatarUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(120.dp)
                        .clickable { showImageSourceDialog = true }
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )

                OutlinedTextField(
                    value = fio,
                    onValueChange = { fio = it },
                    label = { Text("ФИО") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = position,
                    onValueChange = { position = it },
                    label = { Text("Должность") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Почта") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = resumeUrl,
                    onValueChange = { resumeUrl = it },
                    label = { Text("URL резюме") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}
