package at.frank.chuckjokes.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import at.frank.chuckjokes.JokeAppContract
import at.frank.chuckjokes.domain.BookmarkJokeUseCase
import at.frank.chuckjokes.domain.GetRandomJoke
import at.frank.chuckjokes.domain.Joke
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class JokeViewModelTest {
    private val mockedAppContract = object : JokeAppContract {
        override val subscribeOn: Scheduler = Schedulers.trampoline()
        override val observeOn: Scheduler = Schedulers.trampoline()
        override val getRandomJokeUseCase: GetRandomJoke = object : GetRandomJoke {
            override fun invoke(): Observable<Joke> {
                return Observable.fromArray(Joke(value = "this is a joke!"))
            }
        }
        override val bookmarkJokeUseCase: BookmarkJokeUseCase = object : BookmarkJokeUseCase {
            override fun invoke(joke: Joke) = Completable.complete()
        }
    }

    lateinit var viewModel: JokeViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `should contain Loaded state`() {
        viewModel = JokeViewModel(mockedAppContract)
        viewModel.loadInitialJoke()
        assertTrue(viewModel.jokeLiveData.value is JokeViewState.Loaded)
        assertEquals(
            "this is a joke!",
            (viewModel.jokeLiveData.value as JokeViewState.Loaded).joke.value
        )
    }

    @After
    fun teardown() {

    }
}