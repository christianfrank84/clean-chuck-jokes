package at.frank.chuckjokes.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import at.frank.chuckjokes.data.local.JokeDao
import at.frank.chuckjokes.data.local.JokeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val application: Application) {
    @Singleton
    @Provides
    fun providesJokeDatabase(): JokeDatabase = Room.databaseBuilder(
        application,
        JokeDatabase::class.java, "jokeDb"
    ).build()

    @Provides
    fun providesJokeDao(database: JokeDatabase): JokeDao = database.jokeDao()
}