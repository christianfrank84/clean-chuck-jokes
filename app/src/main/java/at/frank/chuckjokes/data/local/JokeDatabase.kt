package at.frank.chuckjokes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [JokeDBEntity::class], version = 1)
abstract class JokeDatabase: RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}