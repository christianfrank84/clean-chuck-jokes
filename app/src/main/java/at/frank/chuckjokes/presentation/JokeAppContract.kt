package at.frank.chuckjokes.presentation

import android.content.Context
import at.frank.chuckjokes.domain.BookmarkJokeUseCase
import at.frank.chuckjokes.domain.GetBookmarkedJokesUseCase
import at.frank.chuckjokes.domain.GetRandomJokeUseCase
import at.frank.chuckjokes.domain.RemoveJokeFromBookmarksUseCase
import io.reactivex.Scheduler

interface JokeAppContract {
    val subscribeOn: Scheduler
    val observeOn: Scheduler

    val getRandomJokeUseCase: GetRandomJokeUseCase
    val bookmarkJokeUseCase: BookmarkJokeUseCase
    val getBookmarkedJokesUseCase: GetBookmarkedJokesUseCase
    val removeJokeFromBookmarksUseCase: RemoveJokeFromBookmarksUseCase
}

fun Context.getJokeApp(): JokeAppContract {
    return this.applicationContext as JokeAppContract
}