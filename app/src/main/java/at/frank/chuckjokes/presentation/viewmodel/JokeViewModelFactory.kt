package at.frank.chuckjokes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.frank.chuckjokes.presentation.JokeAppContract
import at.frank.chuckjokes.presentation.bookmarked.BookmarkedJokesViewModel
import at.frank.chuckjokes.presentation.randomjokes.RandomJokeViewModel

class JokeViewModelFactory(private val app: JokeAppContract) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RandomJokeViewModel::class.java))
            return RandomJokeViewModel(app) as T
        if (modelClass.isAssignableFrom(BookmarkedJokesViewModel::class.java))
            return BookmarkedJokesViewModel(app) as T
        else throw IllegalArgumentException("Unknown ViewModel")
    }
}