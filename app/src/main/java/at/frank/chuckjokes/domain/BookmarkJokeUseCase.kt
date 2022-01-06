package at.frank.chuckjokes.domain

import at.frank.chuckjokes.data.JokeRepository
import io.reactivex.Completable
import io.reactivex.Observable

interface BookmarkJokeUseCase {
    fun invoke(joke: Joke): Completable
}

class BookmarkJokeUseCaseImpl(private val repo: JokeRepository) : BookmarkJokeUseCase {
    override fun invoke(joke: Joke): Completable = repo.bookmarkJoke(joke)
}
