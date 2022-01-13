package at.frank.chuckjokes.unittests

import at.frank.chuckjokes.JokeApp
import at.frank.chuckjokes.di.ApplicationComponent
import at.frank.chuckjokes.unittests.di.DaggerTestAppComponent

class TestApplication : JokeApp() {
    override fun initializeDaggerComponent(): ApplicationComponent {
        return DaggerTestAppComponent.create()
    }
}
