package at.frank.chuckjokes.di

import at.frank.chuckjokes.presentation.bookmarked.BookmarkedJokesFragment
import at.frank.chuckjokes.presentation.randomjokes.RandomJokeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindRandomJokeFragment(): RandomJokeFragment

    @ContributesAndroidInjector
    abstract fun bindBookmarkedJokesFragment(): BookmarkedJokesFragment

}