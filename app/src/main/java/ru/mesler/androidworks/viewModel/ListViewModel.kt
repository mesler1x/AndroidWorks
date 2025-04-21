package ru.mesler.androidworks.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.debounce
import org.koin.java.KoinJavaComponent.inject
import ru.mesler.androidworks.domain.model.Movie
import ru.mesler.androidworks.domain.model.MovieShort
import ru.mesler.androidworks.domain.model.MovieType
import ru.mesler.androidworks.domain.repository.IMoviesRepository
import ru.mesler.androidworks.state.MoviesListState
import ru.mesler.androidworks.utils.launchLoadingAndError
import java.time.Duration

class ListViewModel(
    private val repository: IMoviesRepository,
    private val navigation: NavHostController
) : ViewModel() {
    private val dataStore: DataStore<Preferences> by inject(DataStore::class.java)
    private val typesKey = stringSetPreferencesKey(KEY_MOVIE_TYPES)
    private var filterTypes: Set<MovieType> = emptySet()

    private val mutableState = MutableMoviesListState()
    val viewState = mutableState as MoviesListState

    private val textChangesFlow = MutableStateFlow("")
    init {
        viewModelScope.launch {
            textChangesFlow
                .debounce(Duration.ofSeconds(1L))
                .collect { loadMovies(it) }
        }

        viewModelScope.launch {
            dataStore.data.collect {
                filterTypes = it[typesKey]
                    ?.map { MovieType.valueOf(it) }
                    ?.toSet()
                    .orEmpty()
                updateBadge()
            }
        }

        mutableState.typesVariants = setOf(
            MovieType.MOVIE,
            MovieType.TV_SERIES,
            MovieType.CARTOON,
            MovieType.ANIME,
            MovieType.ANIMATED_SERIES
        )
    }

    private fun loadMovies(query: String) {
        mutableState.items = emptyList()
        mutableState.error = null

        if (query.length < MIN_QUERY_LENGTH_TO_SEARCH) return
        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.isLoading = it }
        ) {
            mutableState.items = repository.getList(query, filterTypes)
        }
    }

    fun onQueryChanged(query: String) {
        mutableState.query = query
        viewModelScope.launch { textChangesFlow.emit(query) }
    }

    fun onItemClicked(id: Int) {
        navigation.navigate("details/$id")
    }

    private class MutableMoviesListState : MoviesListState {
        override var items: List<MovieShort> by mutableStateOf(emptyList())
        override var query by mutableStateOf("")
        override val isEmpty get() = items.isEmpty()
        override var isLoading: Boolean by mutableStateOf(false)
        override var error: String? by mutableStateOf(null)
        override var showTypesDialog: Boolean by mutableStateOf(false)
        override var typesVariants: Set<MovieType> by mutableStateOf(emptySet())
        override var selectedTypes: Set<MovieType> by mutableStateOf(emptySet())
        override var hasBadge: Boolean by mutableStateOf(false)
    }

    companion object {
        private const val MIN_QUERY_LENGTH_TO_SEARCH = 3
        private const val KEY_MOVIE_TYPES = "MOVIE_TYPES"
    }

    fun onFiltersClicked() {
        mutableState.showTypesDialog = true
        mutableState.selectedTypes = filterTypes
    }

    fun onSelectionDialogDismissed() {
        mutableState.showTypesDialog = false
    }

    fun onSelectedVariantChanged(variant: MovieType, selected: Boolean) {
        mutableState.selectedTypes = mutableState.selectedTypes.run {
            if (selected) plus(variant) else minus(variant)
        }
    }

    fun onFiltersConfirmed() {
        if (filterTypes != mutableState.selectedTypes) {
            filterTypes = mutableState.selectedTypes
            loadMovies(textChangesFlow.value)
            updateBadge()

            viewModelScope.launch {
                dataStore.edit { it ->
                    it[typesKey] = filterTypes.map { it.name }.toSet()
                }
            }
        }
        onSelectionDialogDismissed()
    }

    fun onItemDoubleClicked(item: MovieShort) {
        viewModelScope.launch {
            repository.saveFavorite(item)
        }
    }

    private fun updateBadge() {
        mutableState.hasBadge = filterTypes.isNotEmpty()
    }
}