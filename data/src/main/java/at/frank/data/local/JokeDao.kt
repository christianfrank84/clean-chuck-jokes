package at.frank.data.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface JokeDao {
    @Query("SELECT * FROM jokes")
    fun getBookmarkedJokes(): Flowable<List<JokeDBE>>

    @Query("SELECT EXISTS (SELECT 1 FROM jokes WHERE id = :id)")
    fun isBookmarked(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bookmarkJoke(joke: JokeDBE): Completable

    @Delete
    fun removeBookmarkedJoke(joke: JokeDBE): Completable
}