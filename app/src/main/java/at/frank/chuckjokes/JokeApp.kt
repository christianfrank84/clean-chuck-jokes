package at.frank.chuckjokes

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import at.frank.chuckjokes.di.ApplicationComponent
import at.frank.chuckjokes.di.DaggerApplicationComponent
import at.frank.chuckjokes.di.DatabaseModule
import at.frank.chuckjokes.di.NetworkModule

class JokeApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        applicationComponent =
            DaggerApplicationComponent
                .builder()
                .databaseModule(DatabaseModule(this))
                .networkModule(NetworkModule("https://api.chucknorris.io/"))
                .build()

        super.onCreate()
    }
}