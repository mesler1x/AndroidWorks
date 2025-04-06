package ru.mesler.androidworks.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.mesler.androidworks.domain.model.Movie
import ru.mesler.androidworks.domain.model.MovieShort
import ru.mesler.androidworks.domain.repository.IMoviesRepository
import ru.mesler.androidworks.state.MovieDetailState
import ru.mesler.androidworks.utils.launchLoadingAndError

class DetailsViewModel(
    private val repository: IMoviesRepository,
    private val navigation: NavHostController,
    private val id: Int
) : ViewModel() {

    val mutableState = MutableDetailsState()

    init {
        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.isLoading = it }
        ) {
            mutableState.movie = repository.getById(id)
            mutableState.movie?.name?.let {
                launch { mutableState.related = repository.getList(it).take(3) }
            }
        }
    }

    fun back() {
        navigation.popBackStack()
    }

    class MutableDetailsState : MovieDetailState {
        override var movie: Movie? by mutableStateOf(null)
        override var rating: Float by mutableFloatStateOf(0f)
        override val isRatingVisible: Boolean get() = rating != 0f
        override var isLoading: Boolean by mutableStateOf(false)
        override var error: String? by mutableStateOf(null)
        override var related: List<MovieShort> by mutableStateOf(emptyList())
    }
}