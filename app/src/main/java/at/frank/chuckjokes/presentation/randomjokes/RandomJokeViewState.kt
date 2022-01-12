package at.frank.chuckjokes.presentation.randomjokes

import at.frank.chuckjokes.domain.Joke

sealed class RandomJokeViewState {
    class Loaded(val joke: Joke) : RandomJokeViewState()
    object Loading : RandomJokeViewState()
    class Error(val message: String) : RandomJokeViewState()
}