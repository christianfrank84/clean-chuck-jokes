package at.frank.chuckjokes.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import at.frank.chuckjokes.data.local.JokeDBE
import at.frank.chuckjokes.data.local.JokeDao
import at.frank.chuckjokes.data.local.JokeDatabase
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class JokeDatabaseReadWriteTest {
    private lateinit var userDao: JokeDao
    private lateinit var db: JokeDatabase

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, JokeDatabase::class.java
        ).allowMainThreadQueries().build()
        userDao = db.jokeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeJokeToDbAndFetchInList() {
        val joke = JokeDBE("1234", "", "funny joke", "")
        userDao.bookmarkJoke(joke).test().assertComplete().dispose()
        userDao.getBookmarkedJokes().test().assertValue { it.contains(joke) }
            .dispose()
    }

    @Test
    @Throws(Exception::class)
    fun shouldReplaceJokeIfAnotherJokeIsWrittenToDbWithSameId() {
        val joke1 = JokeDBE("1234", "", "funny joke", "")
        userDao.bookmarkJoke(joke1).test().assertComplete().dispose()
        val joke2 = JokeDBE("1234", "", "updatedfunny joke", "")
        userDao.bookmarkJoke(joke2).test().assertComplete().dispose()
        userDao.getBookmarkedJokes().test().assertValue { it.contains(joke2) }
            .dispose()
    }

    @Test
    @Throws(Exception::class)
    fun writeJokeToDbAndSeeIfItExists() {
        val joke = JokeDBE("1234", "", "funny joke", "")
        userDao.bookmarkJoke(joke).test().assertComplete().dispose()
        assertTrue(userDao.isBookmarked(joke.id))
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnFalseIfAJokeDoesntExistInDb() {
        val joke = JokeDBE("1234", "", "funny joke", "")
        assertFalse(userDao.isBookmarked(joke.id))
    }

    @Test
    @Throws(Exception::class)
    fun writeJokeToDbAndDeleteIt() {
        val joke = JokeDBE("1234", "", "funny joke", "")
        userDao.bookmarkJoke(joke).test().assertComplete().dispose()
        userDao.getBookmarkedJokes().test().assertValue { it.contains(joke) }
            .dispose()

        userDao.removeBookmarkedJoke(joke).test().assertComplete().dispose()
        userDao.getBookmarkedJokes()
            .test()
            .assertValue {
                it.isEmpty()
            }.dispose()
    }
}