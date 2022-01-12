package at.frank.chuckjokes.domain

import io.reactivex.Single
import javax.inject.Inject

interface GetRandomJokeUseCase {
    fun invoke(): Single<Joke>
}

class GetRandomJoke @Inject constructor(private val repo: JokeRepository) : GetRandomJokeUseCase {
    override fun invoke(): Single<Joke> = repo.getRandomJoke().flatMap { joke ->
        if (joke.value.isNotEmpty()) Single.just(joke) else Single.error(
            IllegalArgumentException("Joke has no value")
        )
    }
}
