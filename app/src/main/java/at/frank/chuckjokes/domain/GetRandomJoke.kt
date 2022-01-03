package at.frank.chuckjokes.domain

import at.frank.chuckjokes.data.JokeRepository
import io.reactivex.Observable

interface GetRandomJoke {
    fun invoke(): Observable<Joke>
}

class GetRandomJokeImpl(private val repo: JokeRepository) : GetRandomJoke {
    override fun invoke() = repo.getRandomJoke()
}