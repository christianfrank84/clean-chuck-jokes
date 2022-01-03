package at.frank.chuckjokes.data

import at.frank.chuckjokes.domain.Joke
import io.reactivex.Observable

interface JokeRepository {
    fun getRandomJoke(): Observable<Joke>
}

class JokeRepositoryImpl(private val api: ChuckNorrisApi) : JokeRepository {
    override fun getRandomJoke(): Observable<Joke> = api.getRandomChuckNorrisJoke()
}