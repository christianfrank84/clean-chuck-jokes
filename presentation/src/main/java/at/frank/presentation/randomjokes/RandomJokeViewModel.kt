package at.frank.presentation.randomjokes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.frank.presentation.JokeAppContract
import io.reactivex.disposables.CompositeDisposable

class RandomJokeViewModel(
    private val app: JokeAppContract
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val jokeLiveData = MutableLiveData<RandomJokeViewState>()

    val toastLiveData = MutableLiveData<String>()

    fun loadInitialJoke() {
        if (jokeLiveData.value == null || jokeLiveData.value !is RandomJokeViewState.Loaded)
            loadRandomJoke()
    }

    fun loadRandomJoke() {
        jokeLiveData.postValue(RandomJokeViewState.Loading)
        app.getRandomJokeUseCase.invoke()
            .subscribeOn(app.subscribeOn)
            .observeOn(app.observeOn)
            .subscribe(
                { onNext -> jokeLiveData.postValue(RandomJokeViewState.Loaded(onNext)) },
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
        val value = jokeLiveData.value
        if (value != null && value is RandomJokeViewState.Loaded) {
            app.bookmarkJokeUseCase.invoke(value.joke).subscribeOn(app.subscribeOn)
                .observeOn(app.observeOn).subscribe {
                toastLiveData.postValue("Joke added to bookmarks!")
                jokeLiveData.postValue(RandomJokeViewState.Loaded(value.joke))
            }.let { compositeDisposable.add(it) }
        }
    }

    fun removeDisplayedJokeFromBookmarks() {
        TODO("Not yet implemented")
    }
}

