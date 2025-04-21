package ru.mesler.androidworks

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.mesler.androidworks.api.restModule
import ru.mesler.androidworks.di.dbMovieModule
import ru.mesler.androidworks.di.dbProfileModule
import ru.mesler.androidworks.di.rootModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(rootModule, restModule, dbMovieModule, dbProfileModule)
        }
    }
}