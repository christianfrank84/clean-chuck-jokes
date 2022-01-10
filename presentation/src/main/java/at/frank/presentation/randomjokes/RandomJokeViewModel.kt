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

    val jokeLiveData = MutableLiveData<RandomJokeViewState>()

    val toastLiveData = MutableLiveData<String>()

    fun loadInitialJoke() {
        if (currentlyDisplayedJoke() == null)
            loadRandomJoke()
    }

    fun loadRandomJoke() {
        jokeLiveData.postValue(RandomJokeViewState.Loading)
        app.getRandomJokeUseCase.invoke()
            .subscribeOn(app.subscribeOn)
            .observeOn(app.observeOn)
            .subscribe(
                { onNext ->
                    jokeLiveData.postValue(RandomJokeViewState.Loaded(onNext))
                },
                { onError ->
                    jokeLiveData.postValue(
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
                    toastLiveData.postValue("Joke added to bookmarks!")
                    jokeLiveData.postValue(RandomJokeViewState.Loaded(joke.apply {
                        bookmarked = true
                    }))
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
                    toastLiveData.postValue("Joke removed from bookmarks!")
                    jokeLiveData.postValue(RandomJokeViewState.Loaded(joke.apply {
                        bookmarked = false
                    }))
                }
        }
    }

    private fun currentlyDisplayedJoke(): Joke? {
        val currentViewState = jokeLiveData.value
        return if (currentViewState is RandomJokeViewState.Loaded)
            currentViewState.joke
        else null
    }
}

