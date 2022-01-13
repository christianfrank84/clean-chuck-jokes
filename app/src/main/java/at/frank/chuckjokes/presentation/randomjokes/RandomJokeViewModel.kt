package at.frank.chuckjokes.presentation.randomjokes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.frank.chuckjokes.RxSchedulers
import at.frank.chuckjokes.domain.*
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RandomJokeViewModel @Inject constructor(
    private val getRandomJokeUseCase: GetRandomJokeUseCase,
    private val getBookmarkedJokesUseCase: GetBookmarkedJokesUseCase,
    private val bookmarkJokeUseCase: BookmarkJokeUseCase,
    private val removeJokeFromBookmarksUseCase: RemoveJokeFromBookmarksUseCase,
    private val schedulers: RxSchedulers

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
        getBookmarkedJokesUseCase
            .invoke()
            .subscribeOn(schedulers.subscriptionScheduler)
            .observeOn(schedulers.observerScheduler)
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
        getRandomJokeUseCase.invoke()
            .subscribeOn(schedulers.subscriptionScheduler)
            .observeOn(schedulers.observerScheduler)
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
            bookmarkJokeUseCase
                .invoke(joke)
                .subscribeOn(schedulers.subscriptionScheduler)
                .observeOn(schedulers.observerScheduler)
                .subscribe {
                    postJokeToViewState(joke.apply { bookmarked = true })
                }.let { compositeDisposable.add(it) }
        }
    }

    fun removeDisplayedJokeFromBookmarks() {
        currentlyDisplayedJoke()?.let { joke ->
            removeJokeFromBookmarksUseCase
                .invoke(joke)
                .subscribeOn(schedulers.subscriptionScheduler)
                .observeOn(schedulers.observerScheduler)
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

    fun bookmarkPressed() {
        currentlyDisplayedJoke()?.let {
            if (it.bookmarked)
                removeDisplayedJokeFromBookmarks()
            else addDisplayedJokeToBookmarks()
        }
    }
}

