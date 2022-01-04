package at.frank.chuckjokes.domain

import at.frank.chuckjokes.data.JokeRepository
import io.reactivex.Observable

interface GetRandomJoke {
    fun invoke(): Observable<Joke>
}

class GetRandomJokeImpl(private val repo: JokeRepository) : GetRandomJoke {
    override fun invoke(): Observable<Joke> = repo.getRandomJoke().flatMap { joke ->
        if (joke.value.isNotEmpty()) Observable.fromArray(joke) else Observable.error(
            IllegalArgumentException("Joke has no value")
        )
    }
}
