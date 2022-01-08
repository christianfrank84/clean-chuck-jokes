package at.frank.chuckjokes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [JokeDBE::class], version = 1)
abstract class JokeDatabase: RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}