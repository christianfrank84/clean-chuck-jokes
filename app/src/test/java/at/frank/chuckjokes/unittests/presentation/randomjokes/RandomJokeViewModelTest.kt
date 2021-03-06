package at.frank.chuckjokes.unittests.presentation.randomjokes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import at.frank.chuckjokes.RxSchedulers
import at.frank.chuckjokes.domain.*
import at.frank.chuckjokes.presentation.randomjokes.RandomJokeViewModel
import at.frank.chuckjokes.presentation.randomjokes.RandomJokeViewState
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class RandomJokeViewModelTest {

    private val testRxSchedulers = RxSchedulers(Schedulers.trampoline(), Schedulers.trampoline())

    private val getRandomJokeUseCase: GetRandomJokeUseCase =
        object : GetRandomJokeUseCase {
            override fun invoke(): Single<Joke> {
                return Single.just(Joke(value = "this is a joke!"))
            }
        }

    private val bookmarkJokeUseCase: BookmarkJokeUseCase =
        object : BookmarkJokeUseCase {
            override fun invoke(joke: Joke) = Completable.complete()
        }

    private val getBookmarkedJokesUseCase: GetBookmarkedJokesUseCase =
        object : GetBookmarkedJokesUseCase {
            override fun invoke(): Flowable<List<Joke>> = Flowable.just(emptyList())
        }

    private val removeJokeFromBookmarksUseCase: RemoveJokeFromBookmarksUseCase =
        object : RemoveJokeFromBookmarksUseCase {
            override fun invoke(joke: Joke) = Completable.complete()
        }

    lateinit var viewModel: RandomJokeViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `should emit Loaded state if usecase emits a Joke`() {
        viewModel = RandomJokeViewModel(
            getRandomJokeUseCase = getRandomJokeUseCase,
            getBookmarkedJokesUseCase = getBookmarkedJokesUseCase,
            bookmarkJokeUseCase = bookmarkJokeUseCase,
            removeJokeFromBookmarksUseCase = removeJokeFromBookmarksUseCase,
            schedulers = testRxSchedulers
        )
        viewModel.loadInitialJoke()
        assertTrue(viewModel.viewState.value is RandomJokeViewState.Loaded)
        assertEquals(
            "this is a joke!",
            (viewModel.viewState.value as RandomJokeViewState.Loaded).joke.value
        )
    }

    @After
    fun teardown() {

    }
}