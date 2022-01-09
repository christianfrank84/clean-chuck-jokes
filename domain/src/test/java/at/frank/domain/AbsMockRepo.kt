package at.frank.domain

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

open class AbsMockRepo: JokeRepository {
    override fun getRandomJoke(): Observable<Joke> = Observable.fromArray(Joke())
    override fun getBookmarkedJokes() = Single.just(listOf<Joke>())
    override fun bookmarkJoke(joke: Joke) = Completable.complete()
    override fun removeBookmarkedJoke(joke: Joke) = Completable.complete()
}