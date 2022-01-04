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
}

sealed class JokeViewState {
    class Loaded(val joke: Joke) : JokeViewState()
    object Loading : JokeViewState()
    class Error(val message: String) : JokeViewState()
}