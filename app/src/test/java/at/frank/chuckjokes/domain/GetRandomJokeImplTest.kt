package at.frank.chuckjokes.domain

import at.frank.chuckjokes.AbsMockRepo
import io.reactivex.Observable
import org.junit.Test

class GetRandomJokeImplTest
{
    @Test
    fun shouldReturnObservableJokeFromRepository() {
        val expectedJoke = Joke("", "", "Joke", "")
        val useCase = GetRandomJokeImpl(object : AbsMockRepo() {
            override fun getRandomJoke(): Observable<Joke> {
                return Observable.fromArray(expectedJoke)
            }
        })

        useCase.invoke().test().assertValue(expectedJoke).dispose()
    }

    @Test
    fun shouldReturnObservableErrorFromRepository() {
        val useCase = GetRandomJokeImpl(object : AbsMockRepo() {
            override fun getRandomJoke(): Observable<Joke> {
                return Observable.error(Throwable())
            }
        })

        useCase.invoke().test().assertError(Throwable::class.java).dispose()
    }
}