package at.frank.chuckjokes

import at.frank.chuckjokes.data.JokeRepository
import at.frank.chuckjokes.domain.Joke
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

open class AbsMockRepo: JokeRepository {
    override fun getRandomJoke(): Observable<Joke> = Observable.fromArray(Joke())
    override fun getBookmarkedJokes(): Single<List<Joke>> {
        return Single.just(listOf())
    }

    override fun bookmarkJoke(joke: Joke): Completable {
        return Completable.complete()
    }
}