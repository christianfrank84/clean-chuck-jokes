package at.frank.chuckjokes.domain

import at.frank.chuckjokes.data.JokeRepository
import io.reactivex.Completable
import javax.inject.Inject

interface RemoveJokeFromBookmarksUseCase {
    fun invoke(joke: Joke): Completable
}

class RemoveJokeFromBookmarks @Inject constructor(private val repo: JokeRepository) :
    RemoveJokeFromBookmarksUseCase {
    override fun invoke(joke: Joke): Completable = repo.removeBookmarkedJoke(joke.mapToDBE())
}
