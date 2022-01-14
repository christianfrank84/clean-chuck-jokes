package at.frank.chuckjokes.di

import at.frank.chuckjokes.data.local.JokeDao
import at.frank.chuckjokes.data.remote.ChuckNorrisApi
import at.frank.chuckjokes.data.JokeRepository
import at.frank.chuckjokes.data.JokeRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providesJokeRepository(api: ChuckNorrisApi, dao: JokeDao): JokeRepository =
        JokeRepositoryImpl(api, dao)
}