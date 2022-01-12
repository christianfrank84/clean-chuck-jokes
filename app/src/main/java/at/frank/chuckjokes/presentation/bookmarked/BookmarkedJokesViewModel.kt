package at.frank.chuckjokes.presentation.bookmarked

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.frank.chuckjokes.domain.Joke
import at.frank.chuckjokes.presentation.JokeAppContract
import io.reactivex.disposables.CompositeDisposable

class BookmarkedJokesViewModel(
    private val app: JokeAppContract
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val viewState = MutableLiveData<BookmarkedJokesViewState>()


    fun loadBookmarkedJokes() {
        viewState.postValue(BookmarkedJokesViewState.Loading)
        app.getBookmarkedJokesUseCase.invoke()
            .subscribeOn(app.subscribeOn)
            .observeOn(app.observeOn)
            .subscribe(
                { onNext ->
                    viewState.postValue(BookmarkedJokesViewState.Loaded(onNext))
                },
                { onError ->
                    viewState.postValue(
                        BookmarkedJokesViewState.Error(
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

    fun removeJokeFromBookmarks(joke: Joke) {
        app.removeJokeFromBookmarksUseCase.invoke(joke).subscribeOn(app.subscribeOn)
            .observeOn(app.observeOn).subscribe {

            }.let { compositeDisposable.add(it) }
    }
}

