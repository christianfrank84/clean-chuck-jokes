package at.frank.chuckjokes

import at.frank.chuckjokes.domain.BookmarkJokeUseCase
import at.frank.chuckjokes.domain.GetRandomJokeUseCase
import io.reactivex.Scheduler

interface JokeAppContract {
    val subscribeOn: Scheduler
    val observeOn: Scheduler

    val getRandomJokeUseCase: GetRandomJokeUseCase
    val bookmarkJokeUseCase: BookmarkJokeUseCase
}