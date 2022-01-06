package at.frank.chuckjokes

import at.frank.chuckjokes.domain.BookmarkJokeUseCase
import at.frank.chuckjokes.domain.GetRandomJoke
import io.reactivex.Scheduler

interface JokeAppContract {
    val subscribeOn: Scheduler
    val observeOn: Scheduler

    val getRandomJokeUseCase: GetRandomJoke
    val bookmarkJokeUseCase: BookmarkJokeUseCase
}