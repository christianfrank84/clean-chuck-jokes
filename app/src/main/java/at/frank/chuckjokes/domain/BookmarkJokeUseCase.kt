package at.frank.chuckjokes.domain

import io.reactivex.Completable

interface BookmarkJokeUseCase {
    fun invoke(joke: Joke): Completable
}

class BookmarkJokeUseCaseImpl(private val repo: JokeRepository) : BookmarkJokeUseCase {
    override fun invoke(joke: Joke): Completable = repo.bookmarkJoke(joke)
}
