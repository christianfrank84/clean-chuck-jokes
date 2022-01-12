package at.frank.chuckjokes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.frank.chuckjokes.RxSchedulers
import at.frank.chuckjokes.domain.BookmarkJokeUseCase
import at.frank.chuckjokes.domain.GetBookmarkedJokesUseCase
import at.frank.chuckjokes.domain.GetRandomJokeUseCase
import at.frank.chuckjokes.domain.RemoveJokeFromBookmarksUseCase
import at.frank.chuckjokes.presentation.bookmarked.BookmarkedJokesViewModel
import at.frank.chuckjokes.presentation.randomjokes.RandomJokeViewModel

class JokeViewModelFactory(
    private val getBookmarkedJokesUseCase: GetBookmarkedJokesUseCase,
    private val getRandomJokeUseCase: GetRandomJokeUseCase,
    private val bookmarkJokeUseCase: BookmarkJokeUseCase,
    private val removeJokeFromBookmarksUseCase: RemoveJokeFromBookmarksUseCase,
    private val rxSchedulers: RxSchedulers
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RandomJokeViewModel::class.java))
            return RandomJokeViewModel(
                getRandomJokeUseCase,
                getBookmarkedJokesUseCase,
                bookmarkJokeUseCase,
                removeJokeFromBookmarksUseCase,
                rxSchedulers
            ) as T
        if (modelClass.isAssignableFrom(BookmarkedJokesViewModel::class.java))
            return BookmarkedJokesViewModel(getRandomJokeUseCase,
                getBookmarkedJokesUseCase,
                bookmarkJokeUseCase,
                removeJokeFromBookmarksUseCase,
                rxSchedulers) as T
        else throw IllegalArgumentException("Unknown ViewModel")
    }
}