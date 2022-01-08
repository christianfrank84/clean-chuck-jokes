package at.frank.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import at.frank.data.local.JokeDBE
import at.frank.data.local.JokeDao
import at.frank.data.local.JokeDatabase
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
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

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, JokeDatabase::class.java
        ).build()
        userDao = db.jokeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val joke = JokeDBE("1234", "", "funny joke", "")
        userDao.bookmarkJoke(joke).test()
        userDao.getBookmarkedJokes().test().assertComplete().assertValue { it.contains(joke) }
            .dispose()
    }
}