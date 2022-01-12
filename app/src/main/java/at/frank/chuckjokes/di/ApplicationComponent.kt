package at.frank.chuckjokes.di

import at.frank.chuckjokes.presentation.bookmarked.BookmarkedJokesFragment
import at.frank.chuckjokes.presentation.randomjokes.RandomJokeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UseCasesModule::class]
)
interface ApplicationComponent {
    fun injectRandomJokeFragment(fragment: RandomJokeFragment)
    fun injectBookmarkedJokesFragment(fragment: BookmarkedJokesFragment)
}