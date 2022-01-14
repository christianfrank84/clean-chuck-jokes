package at.frank.chuckjokes.unittests.domain

import at.frank.chuckjokes.domain.Joke
import at.frank.chuckjokes.data.JokeRepository
import at.frank.chuckjokes.data.local.JokeDBE
import at.frank.chuckjokes.data.remote.JokeDTO
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

open class AbsMockRepo: JokeRepository {
    override fun getRandomJoke(): Single<JokeDTO> = Single.just(JokeDTO())
    override fun getBookmarkedJokes() = Flowable.just(listOf<JokeDBE>())
    override fun isJokeBookmarked(id: String): Boolean = false
    override fun bookmarkJoke(joke: Joke) = Completable.complete()
    override fun removeBookmarkedJoke(joke: Joke) = Completable.complete()
}