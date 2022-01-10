package at.frank.presentation.bookmarked

import at.frank.domain.Joke

sealed class BookmarkedJokesViewState {
    class Loaded(val jokes: List<Joke>) : BookmarkedJokesViewState()
    object Loading : BookmarkedJokesViewState()
    class Error(val message: String) : BookmarkedJokesViewState()
}