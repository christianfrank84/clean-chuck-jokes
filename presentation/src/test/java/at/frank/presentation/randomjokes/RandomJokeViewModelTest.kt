package at.frank.presentation.randomjokes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import at.frank.domain.*
import at.frank.presentation.JokeAppContract
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class RandomJokeViewModelTest {
    private val mockedAppContract = object : JokeAppContract {
        override val subscribeOn: Scheduler = Schedulers.trampoline()
        override val observeOn: Scheduler = Schedulers.trampoline()
        override val getRandomJokeUseCase: GetRandomJokeUseCase = object : GetRandomJokeUseCase {
            override fun invoke(): Single<Joke> {
                return Single.just(Joke(value = "this is a joke!"))
            }
        }
        override val bookmarkJokeUseCase: BookmarkJokeUseCase = object : BookmarkJokeUseCase {
            override fun invoke(joke: Joke) = Completable.complete()
        }
        override val getBookmarkedJokesUseCase: GetBookmarkedJokesUseCase =
            object : GetBookmarkedJokesUseCase {
                override fun invoke(): Flowable<List<Joke>> = Flowable.just(emptyList())

            }
        override val removeJokeFromBookmarksUseCase: RemoveJokeFromBookmarksUseCase =
            object : RemoveJokeFromBookmarksUseCase {
                override fun invoke(joke: Joke) = Completable.complete()

            }
    }

    lateinit var viewModel: RandomJokeViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `should emit Loaded state if usecase emits a Joke`() {
        viewModel = RandomJokeViewModel(mockedAppContract)
        viewModel.loadInitialJoke()
        assertTrue(viewModel.jokeLiveData.value is RandomJokeViewState.Loaded)
        assertEquals(
            "this is a joke!",
            (viewModel.jokeLiveData.value as RandomJokeViewState.Loaded).joke.value
        )
    }

    @After
    fun teardown() {

    }
}