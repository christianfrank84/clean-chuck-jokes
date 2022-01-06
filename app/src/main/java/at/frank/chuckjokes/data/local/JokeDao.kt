package at.frank.chuckjokes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface JokeDao {
    @Query("SELECT * FROM jokes")
    fun getBookmarkedJokes(): Single<List<JokeDBEntity>>

    @Insert
    fun bookmarkJoke(joke: JokeDBEntity): Completable

    @Delete
    fun removeBookmarkedJoke(joke: JokeDBEntity): Completable
}