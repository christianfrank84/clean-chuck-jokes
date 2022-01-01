package at.frank.chuckjokes.data

import at.frank.chuckjokes.domain.JokeRepository
import io.reactivex.Observable

interface GetRandomJoke {
    suspend fun invoke(): Observable<Joke>
}

class GetRandomJokeImpl(private val repo: JokeRepository) : GetRandomJoke {
    override suspend fun invoke() = repo.getRandomJoke()
}