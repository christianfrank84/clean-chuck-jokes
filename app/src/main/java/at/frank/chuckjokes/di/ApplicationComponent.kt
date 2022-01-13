package at.frank.chuckjokes.di

import at.frank.chuckjokes.JokeApp
import at.frank.chuckjokes.presentation.bookmarked.BookmarkedJokesFragment
import at.frank.chuckjokes.presentation.randomjokes.RandomJokeFragment
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UseCasesModule::class,
        FragmentModule::class]
)
interface ApplicationComponent : AndroidInjector<DaggerApplication> {
    fun inject(app: JokeApp)
}