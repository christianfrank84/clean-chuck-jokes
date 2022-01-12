package at.frank.chuckjokes

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import at.frank.chuckjokes.data.local.JokeDatabase
import at.frank.chuckjokes.data.remote.ChuckNorrisApi
import at.frank.chuckjokes.data.remote.RetroFitModule
import at.frank.chuckjokes.di.ApplicationComponent
import at.frank.chuckjokes.di.ApplicationModule
import at.frank.chuckjokes.di.DaggerApplicationComponent
import at.frank.chuckjokes.di.DatabaseModule
import at.frank.chuckjokes.domain.*
import at.frank.chuckjokes.presentation.JokeAppContract
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class JokeApp : Application() {

    lateinit var applicationComponent: ApplicationComponent



    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        applicationComponent =
            DaggerApplicationComponent
                .builder()
                .databaseModule(DatabaseModule(this))
                .build()

        super.onCreate()
    }

}