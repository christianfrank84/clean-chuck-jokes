package at.frank.chuckjokes.domain

import io.reactivex.Completable
import javax.inject.Inject

interface BookmarkJokeUseCase {
    fun invoke(joke: Joke): Completable
}

class BookmarkJoke @Inject constructor(private val repo: JokeRepository) : BookmarkJokeUseCase {
    override fun invoke(joke: Joke): Completable = repo.bookmarkJoke(joke)
}
