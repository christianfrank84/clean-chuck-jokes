package at.frank.domain

import at.frank.data.local.JokeDBE
import at.frank.data.local.JokeDao
import at.frank.data.remote.ChuckNorrisApi
import at.frank.data.remote.JokeDTO
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Test

class JokeRepositoryImplTest {

    @Test
    fun `should return Joke with bookmark=true if it is in Database`() {
        val expectedId = "id"

        val api = object : ChuckNorrisApi {
            override fun getRandomChuckNorrisJoke() = Single.just(JokeDTO(id = expectedId))

        }

        val dao = object : JokeDao {
            override fun getBookmarkedJokes() = Flowable.just(listOf(JokeDBE(id = expectedId)))
            override fun isBookmarked(id: String): Boolean {
                return id == expectedId
            }

            override fun bookmarkJoke(joke: JokeDBE) = Completable.complete()
            override fun removeBookmarkedJoke(joke: JokeDBE): Completable = Completable.complete()

        }

        val repositoryImpl = JokeRepositoryImpl(api, dao)

        repositoryImpl.getRandomJoke()
            .test()
            .assertValue {
                it.bookmarked
            }
    }
}