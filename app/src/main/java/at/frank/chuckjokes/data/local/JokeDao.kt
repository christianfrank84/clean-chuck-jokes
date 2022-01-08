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
    fun getBookmarkedJokes(): Single<List<JokeDBE>>

    @Insert
    fun bookmarkJoke(joke: JokeDBE): Completable

    @Delete
    fun removeBookmarkedJoke(joke: JokeDBE): Completable
}