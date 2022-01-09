package at.frank.domain


import io.reactivex.Single
import org.junit.Test

class GetRandomJokeUseCaseImplTest {
    @Test
    fun shouldReturnObservableJokeFromRepository() {
        val expectedJoke = Joke("", "", "Joke", "")
        val useCase = GetRandomJokeUseCaseImpl(object : AbsMockRepo() {
            override fun getRandomJoke(): Single<Joke> {
                return Single.just(expectedJoke)
            }
        })

        useCase.invoke().test().assertValue(expectedJoke).dispose()
    }

    @Test
    fun shouldReturnObservableErrorFromRepository() {
        val useCase = GetRandomJokeUseCaseImpl(object : AbsMockRepo() {
            override fun getRandomJoke(): Single<Joke> {
                return Single.error(Throwable())
            }
        })

        useCase.invoke().test().assertError(Throwable::class.java).dispose()
    }

    @Test
    fun shouldReturnObservableErrorIfJokeHasNoValues() {
        val useCase = GetRandomJokeUseCaseImpl(object : AbsMockRepo() {
            override fun getRandomJoke(): Single<Joke> {
                return Single.just(Joke())
            }
        })

        useCase.invoke().test().assertNotComplete()
            .assertError(IllegalArgumentException::class.java).dispose()
    }
}