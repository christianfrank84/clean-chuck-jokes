package at.frank.presentation.bookmarked.recyclerview

import at.frank.domain.Joke

interface DeleteJokeFromBookmarksListener {
    fun onDeleteJoke(joke: Joke)
}