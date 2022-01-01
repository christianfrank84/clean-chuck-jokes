package at.frank.chuckjokes.domain

import at.frank.chuckjokes.data.Joke
import io.reactivex.Observable

interface JokeRepository {
    fun getRandomJoke(): Observable<Joke>
}

class JokeRepositoryImpl(private val api: ChuckNorrisApi) : JokeRepository {
    override fun getRandomJoke(): Observable<Joke> = api.getRandomChuckNorrisJoke()
}