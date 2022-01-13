package at.frank.chuckjokes

import androidx.appcompat.app.AppCompatDelegate
import at.frank.chuckjokes.di.ApplicationComponent
import at.frank.chuckjokes.di.DaggerApplicationComponent
import at.frank.chuckjokes.di.DatabaseModule
import at.frank.chuckjokes.di.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class JokeApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return initializeDaggerComponent()
    }

    open fun initializeDaggerComponent(): ApplicationComponent {
        val component = DaggerApplicationComponent
            .builder()
            .databaseModule(DatabaseModule(this))
            .networkModule(NetworkModule("https://api.chucknorris.io/"))
            .build()
        component.inject(this)
        return component
    }
}