package at.frank.chuckjokes.domain

import io.reactivex.Completable

interface RemoveJokeFromBookmarksUseCase {
    fun invoke(joke: Joke): Completable
}

class RemoveJokeFromBookmarks(private val repo: JokeRepository) : RemoveJokeFromBookmarksUseCase {
    override fun invoke(joke: Joke): Completable = repo.removeBookmarkedJoke(joke)
}
