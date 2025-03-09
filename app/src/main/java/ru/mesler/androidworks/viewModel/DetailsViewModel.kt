package ru.mesler.androidworks.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import ru.mesler.androidworks.domain.model.Movie
import ru.mesler.androidworks.domain.repository.IMoviesRepository

class DetailsViewModel(
    private val repository: IMoviesRepository,
    private val navigation: NavHostController,
    private val id: Int
) : ViewModel() {

    val mutableState = MutableDetailsState()

    init {
        mutableState.movie = repository.getById(id)
    }

    fun back() {
        navigation.popBackStack()
    }

    class MutableDetailsState {
        var movie: Movie? by mutableStateOf(null)
    }
}