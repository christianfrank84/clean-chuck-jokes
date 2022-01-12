package at.frank.chuckjokes.unittests.domain

import at.frank.chuckjokes.domain.Joke
import at.frank.chuckjokes.domain.JokeRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

open class AbsMockRepo: JokeRepository {
    override fun getRandomJoke(): Single<Joke> = Single.just(Joke())
    override fun getBookmarkedJokes() = Flowable.just(listOf<Joke>())
    override fun bookmarkJoke(joke: Joke) = Completable.complete()
    override fun removeBookmarkedJoke(joke: Joke) = Completable.complete()
}