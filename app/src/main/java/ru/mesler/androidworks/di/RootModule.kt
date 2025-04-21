package ru.mesler.androidworks.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.mesler.androidworks.domain.repository.IMoviesRepository
import ru.mesler.androidworks.domain.repository.MoviesRepository
import ru.mesler.androidworks.data.mapper.MovieMapper
import ru.mesler.androidworks.viewModel.DetailsViewModel
import ru.mesler.androidworks.viewModel.ListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import ru.mesler.androidworks.domain.repository.IProfileRepository
import ru.mesler.androidworks.domain.repository.ProfileRepository
import ru.mesler.androidworks.viewModel.FavoritesViewModel
import ru.mesler.androidworks.viewModel.ProfileViewModel

val rootModule = module {
    single {
        getSharedPrefs(androidApplication())
    }

    single<SharedPreferences.Editor> {
        getSharedPrefs(androidApplication()).edit()
    }

    single {
        getDataStore(androidContext())
    }

    single<IMoviesRepository> { MoviesRepository(get(), get(), get()) }

    single<IProfileRepository> { ProfileRepository(get()) }

    factory { MovieMapper() }

    viewModel { ListViewModel(get(), it.get()) }
    viewModel { DetailsViewModel(get(), it.get(), it.get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { ProfileViewModel(get(), it.get()) }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences("default", Context.MODE_PRIVATE)
}

fun getDataStore(androidContext: Context): DataStore<Preferences> =
    PreferenceDataStoreFactory.create {
        androidContext.preferencesDataStoreFile("default")
    }