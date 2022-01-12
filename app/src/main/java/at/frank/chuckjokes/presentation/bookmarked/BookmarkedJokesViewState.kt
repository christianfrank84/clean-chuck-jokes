package at.frank.chuckjokes.presentation.bookmarked

import at.frank.chuckjokes.domain.Joke

sealed class BookmarkedJokesViewState {
    class Loaded(val jokes: List<Joke>) : BookmarkedJokesViewState()
    object Loading : BookmarkedJokesViewState()
    class Error(val message: String) : BookmarkedJokesViewState()
}