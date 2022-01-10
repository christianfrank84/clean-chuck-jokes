package at.frank.presentation.randomjokes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.frank.domain.Joke
import at.frank.presentation.JokeAppContract
import io.reactivex.disposables.CompositeDisposable

class RandomJokeViewModel(
    private val app: JokeAppContract
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val viewState = MutableLiveData<RandomJokeViewState>()

    fun loadInitialJoke() {
        if (currentlyDisplayedJoke() == null) {
            loadRandomJoke()
            observeBookmarks()
        }
    }

    private fun observeBookmarks() {
        app.getBookmarkedJokesUseCase
            .invoke()
            .subscribeOn(app.subscribeOn)
            .observeOn(app.observeOn)
            .subscribe(
                { bookmarkedJokes ->
                    currentlyDisplayedJoke()?.let { currentJoke ->
                        bookmarkedJokes.find { item -> item.id == currentJoke.id }?.let {
                            postJokeToViewState(it.apply { bookmarked = true })
                        } ?: postJokeToViewState(currentJoke.apply { bookmarked = false })
                    }
                },
                { })
            .let { compositeDisposable.add(it) }
    }

    fun loadRandomJoke() {
        viewState.postValue(RandomJokeViewState.Loading)
        app.getRandomJokeUseCase.invoke()
            .subscribeOn(app.subscribeOn)
            .observeOn(app.observeOn)
            .subscribe(
                { onNext -> postJokeToViewState(onNext) },
                { onError ->
                    viewState.postValue(
                        RandomJokeViewState.Error(
                            onError.message ?: "Error"
                        )
                    )
                })
            .let { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun addDisplayedJokeToBookmarks() {
        currentlyDisplayedJoke()?.let { joke ->
            app.bookmarkJokeUseCase
                .invoke(joke)
                .subscribeOn(app.subscribeOn)
                .observeOn(app.observeOn)
                .subscribe {
                    postJokeToViewState(joke.apply { bookmarked = true })
                }.let { compositeDisposable.add(it) }
        }
    }

    fun removeDisplayedJokeFromBookmarks() {
        currentlyDisplayedJoke()?.let { joke ->
            app.removeJokeFromBookmarksUseCase
                .invoke(joke)
                .subscribeOn(app.subscribeOn)
                .observeOn(app.observeOn)
                .subscribe {
                    postJokeToViewState(joke.apply { bookmarked = false })
                }
        }
    }

    private fun currentlyDisplayedJoke(): Joke? {
        val currentViewState = viewState.value
        return if (currentViewState is RandomJokeViewState.Loaded)
            currentViewState.joke
        else null
    }

    private fun postJokeToViewState(joke: Joke) {
        viewState.postValue(RandomJokeViewState.Loaded(joke))
    }
}

