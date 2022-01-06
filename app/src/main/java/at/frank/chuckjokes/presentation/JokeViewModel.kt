package at.frank.chuckjokes.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.frank.chuckjokes.JokeAppContract
import at.frank.chuckjokes.domain.Joke
import io.reactivex.disposables.CompositeDisposable

class JokeViewModel(
    private val app: JokeAppContract
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val jokeLiveData = MutableLiveData<JokeViewState>()

    val toastLiveData = MutableLiveData<String>()

    fun loadInitialJoke() {
        if (jokeLiveData.value == null || jokeLiveData.value !is JokeViewState.Loaded)
            loadRandomJoke()
    }

    fun loadRandomJoke() {
        jokeLiveData.postValue(JokeViewState.Loading)
        app.getRandomJokeUseCase.invoke()
            .subscribeOn(app.subscribeOn)
            .observeOn(app.observeOn)
            .subscribe(
                { onNext -> jokeLiveData.postValue(JokeViewState.Loaded(onNext)) },
                { onError ->
                    jokeLiveData.postValue(
                        JokeViewState.Error(
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
        val value = jokeLiveData.value
        if (value != null && value is JokeViewState.Loaded) {
            app.bookmarkJokeUseCase.invoke(value.joke).subscribeOn(app.subscribeOn)
                .observeOn(app.observeOn).subscribe {
                toastLiveData.postValue("Joke added to bookmarks!")
                jokeLiveData.postValue(JokeViewState.Loaded(value.joke.apply {
                    isBookmarked = true
                }))
            }.let { compositeDisposable.add(it) }
        }
    }

    fun removeDisplayedJokeFromBookmarks() {
        TODO("Not yet implemented")
    }
}

sealed class JokeViewState {
    class Loaded(val joke: Joke) : JokeViewState()
    object Loading : JokeViewState()
    class Error(val message: String) : JokeViewState()
}