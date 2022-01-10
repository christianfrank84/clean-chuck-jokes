package at.frank.domain

import io.reactivex.Single

interface GetRandomJokeUseCase {
    fun invoke(): Single<Joke>
}

class GetRandomJoke(private val repo: JokeRepository) : GetRandomJokeUseCase {
    override fun invoke(): Single<Joke> = repo.getRandomJoke().flatMap { joke ->
        if (joke.value.isNotEmpty()) Single.just(joke) else Single.error(
            IllegalArgumentException("Joke has no value")
        )
    }
}
