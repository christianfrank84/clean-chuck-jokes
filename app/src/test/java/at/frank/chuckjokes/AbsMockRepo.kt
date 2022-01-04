package at.frank.chuckjokes

import at.frank.chuckjokes.data.JokeRepository
import at.frank.chuckjokes.domain.Joke
import io.reactivex.Observable

open class AbsMockRepo: JokeRepository {
    override fun getRandomJoke(): Observable<Joke> = Observable.fromArray(Joke("","","",""))
}