package at.frank.presentation.randomjokes

import at.frank.domain.Joke

sealed class RandomJokeViewState {
    class Loaded(val joke: Joke) : RandomJokeViewState()
    object Loading : RandomJokeViewState()
    class Error(val message: String) : RandomJokeViewState()
}