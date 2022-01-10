package at.frank.presentation

import android.content.Context
import at.frank.domain.BookmarkJokeUseCase
import at.frank.domain.GetBookmarkedJokesUseCase
import at.frank.domain.GetRandomJokeUseCase
import at.frank.domain.RemoveJokeFromBookmarksUseCase
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