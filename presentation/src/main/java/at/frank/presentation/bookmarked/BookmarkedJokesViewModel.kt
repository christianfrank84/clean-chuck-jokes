package at.frank.presentation.bookmarked

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.frank.domain.Joke
import at.frank.presentation.JokeAppContract
import io.reactivex.disposables.CompositeDisposable

class BookmarkedJokesViewModel(
    private val app: JokeAppContract
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val viewState = MutableLiveData<BookmarkedJokesViewState>()


    fun loadInitialJoke() {
        if (currentlyDisplayedJokes() == null)
            loadBookmarkedJokes()
    }

    fun loadBookmarkedJokes() {
        viewState.postValue(BookmarkedJokesViewState.Loading)
//        app..invoke()
//            .subscribeOn(app.subscribeOn)
//            .observeOn(app.observeOn)
//            .subscribe(
//                { onNext ->
//                    jokeLiveData.postValue(RandomJokeViewState.Loaded(onNext))
//                },
//                { onError ->
//                    jokeLiveData.postValue(
//                        RandomJokeViewState.Error(
//                            onError.message ?: "Error"
//                        )
//                    )
//                })
//            .let { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun removeDisplayedJokeFromBookmarks() {
        TODO("Not yet implemented")
    }

    private fun currentlyDisplayedJokes(): List<Joke>? {
        val currentViewState = viewState.value
        return if (currentViewState is BookmarkedJokesViewState.Loaded)
            currentViewState.jokes
        else null
    }
}

