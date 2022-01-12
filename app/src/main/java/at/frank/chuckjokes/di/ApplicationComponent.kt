package at.frank.chuckjokes.di

import at.frank.chuckjokes.JokeApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(fragment: JokeApp)
}