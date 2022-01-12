package at.frank.chuckjokes.presentation.bookmarked

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.frank.chuckjokes.RxSchedulers
import at.frank.chuckjokes.domain.*
import io.reactivex.disposables.CompositeDisposable

class BookmarkedJokesViewModel(
    private val getRandomJokeUseCase: GetRandomJokeUseCase,
    private val getBookmarkedJokesUseCase: GetBookmarkedJokesUseCase,
    private val bookmarkJokeUseCase: BookmarkJokeUseCase,
    private val removeJokeFromBookmarksUseCase: RemoveJokeFromBookmarksUseCase,
    private val schedulers: RxSchedulers
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val viewState = MutableLiveData<BookmarkedJokesViewState>()


    fun loadBookmarkedJokes() {
        viewState.postValue(BookmarkedJokesViewState.Loading)
        getBookmarkedJokesUseCase.invoke()
            .subscribeOn(schedulers.subscriptionScheduler)
            .observeOn(schedulers.observerScheduler)
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
        removeJokeFromBookmarksUseCase
            .invoke(joke)
            .subscribeOn(schedulers.subscriptionScheduler)
            .observeOn(schedulers.observerScheduler)
            .subscribe {

            }.let { compositeDisposable.add(it) }
    }
}

