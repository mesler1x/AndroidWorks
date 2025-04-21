package ru.mesler.androidworks.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mesler.androidworks.domain.repository.IMoviesRepository
import ru.mesler.androidworks.state.FavoritesState

class FavoritesViewModel(
    private val repository: IMoviesRepository
) : ViewModel() {
    private var mutableState = MutableStateFlow(FavoritesState())
    val viewState = mutableState.asStateFlow()

    init {
        updateFavorites()
    }

    fun onUpdateClick() {
        updateFavorites()
    }

    private fun updateFavorites() {
        viewModelScope.launch {
            mutableState.update { it.copy(items = repository.getFavorites()) }
        }
    }
}