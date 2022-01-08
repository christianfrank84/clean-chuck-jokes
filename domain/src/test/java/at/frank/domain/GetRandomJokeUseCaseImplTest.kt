package at.frank.domain


import io.reactivex.Observable
import org.junit.Test
import java.lang.IllegalArgumentException

class GetRandomJokeUseCaseImplTest
{
    @Test
    fun shouldReturnObservableJokeFromRepository() {
        val expectedJoke = Joke("", "", "Joke", "")
        val useCase = GetRandomJokeUseCaseImpl(object : AbsMockRepo() {
            override fun getRandomJoke(): Observable<Joke> {
                return Observable.fromArray(expectedJoke)
            }
        })

        useCase.invoke().test().assertValue(expectedJoke).dispose()
    }

    @Test
    fun shouldReturnObservableErrorFromRepository() {
        val useCase = GetRandomJokeUseCaseImpl(object : AbsMockRepo() {
            override fun getRandomJoke(): Observable<Joke> {
                return Observable.error(Throwable())
            }
        })

        useCase.invoke().test().assertError(Throwable::class.java).dispose()
    }

    @Test
    fun shouldReturnObservableErrorIfJokeHasNoValues() {
        val useCase = GetRandomJokeUseCaseImpl(object : AbsMockRepo() {
            override fun getRandomJoke(): Observable<Joke> {
                return Observable.fromArray(Joke())
            }
        })

        useCase.invoke().test().assertNotComplete().assertError(IllegalArgumentException::class.java).dispose()
    }
}