package at.frank.chuckjokes.unittests.domain


import at.frank.chuckjokes.data.remote.JokeDTO
import at.frank.chuckjokes.domain.GetRandomJoke
import at.frank.chuckjokes.domain.Joke
import io.reactivex.Single
import org.junit.Test

class GetRandomJokeTest {
    @Test
    fun `given that repository emits a JokeDTO, Use case should map it to Joke Entity`() {
        val expectedJoke = Joke(value = "Joke", bookmarked = false)
        val useCase = GetRandomJoke(object : AbsMockRepo() {
            override fun getRandomJoke(): Single<JokeDTO> {
                return Single.just(expectedJoke.mapToDTO())
            }
        })

        useCase.invoke().test().assertValue(expectedJoke).dispose()
    }

    @Test
    fun `given that repository emits a Joke that is already bookmarked, Use case should map it to bookmarked Joke Entity`() {
        val expectedJoke = Joke(value = "Joke", bookmarked = true)
        val useCase = GetRandomJoke(object : AbsMockRepo() {
            override fun getRandomJoke(): Single<JokeDTO> {
                return Single.just(expectedJoke.mapToDTO())
            }

            override fun isJokeBookmarked(id: String): Boolean = true

        })

        useCase.invoke().test().assertValue(expectedJoke).dispose()
    }


    @Test
    fun `given that repository emits an error, Use case emit same error`() {
        val useCase = GetRandomJoke(object : AbsMockRepo() {
            override fun getRandomJoke(): Single<JokeDTO> {
                return Single.error(Throwable())
            }
        })

        useCase.invoke().test().assertError(Throwable::class.java).dispose()
    }
}