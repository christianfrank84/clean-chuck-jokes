package at.frank.chuckjokes.di

import android.content.Context
import androidx.room.Room
import at.frank.chuckjokes.data.local.JokeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesJokeDatabase(applicationContext: Context): JokeDatabase = Room.databaseBuilder(
        applicationContext,
        JokeDatabase::class.java, "jokeDb"
    ).build()
}