package ru.mesler.androidworks.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.mesler.androidworks.domain.repository.IMoviesRepository
import ru.mesler.androidworks.domain.repository.MoviesRepository
import ru.mesler.androidworks.mapper.MovieMapper
import ru.mesler.androidworks.viewModel.DetailsViewModel
import ru.mesler.androidworks.viewModel.ListViewModel

val rootModule = module {
    single<IMoviesRepository> { MoviesRepository(get(), get()) }

    factory { MovieMapper() }

    viewModel { ListViewModel(get(), it.get()) }
    viewModel { DetailsViewModel(get(), it.get(), it.get()) }
}
