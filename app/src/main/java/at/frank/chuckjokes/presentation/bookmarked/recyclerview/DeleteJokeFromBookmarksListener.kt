package at.frank.chuckjokes.presentation.bookmarked.recyclerview

import at.frank.chuckjokes.domain.Joke

interface DeleteJokeFromBookmarksListener {
    fun onDeleteJoke(joke: Joke)
}