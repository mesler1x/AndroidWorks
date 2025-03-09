package ru.mesler.androidworks.viewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import ru.mesler.androidworks.domain.model.Movie
import ru.mesler.androidworks.domain.repository.IMoviesRepository

class ListViewModel(
    private val repository: IMoviesRepository,
    private val navigation: NavHostController
) : ViewModel() {

    init {
        loadMovies()
    }

    fun loadMovies(): List<Movie> {
        return repository.getList()
    }

    fun onItemClicked(id: Int) {
        navigation.navigate("details/$id")
    }
}