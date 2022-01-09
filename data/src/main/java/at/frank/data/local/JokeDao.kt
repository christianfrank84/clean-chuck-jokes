package at.frank.data.local

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

    @Query("SELECT EXISTS (SELECT 1 FROM jokes WHERE id = :id)")
    fun isBookmarked(id: String): Single<Boolean>

    @Insert
    fun bookmarkJoke(joke: JokeDBE): Completable

    @Delete
    fun removeBookmarkedJoke(joke: JokeDBE): Completable
}