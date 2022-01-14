package at.frank.chuckjokes.integrationtests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import at.frank.chuckjokes.RxSchedulers
import at.frank.chuckjokes.data.local.JokeDBE
import at.frank.chuckjokes.data.local.JokeDao
import at.frank.chuckjokes.data.remote.ChuckNorrisApi
import at.frank.chuckjokes.data.remote.JokeDTO
import at.frank.chuckjokes.domain.*
import at.frank.chuckjokes.presentation.randomjokes.RandomJokeViewModel
import at.frank.chuckjokes.presentation.randomjokes.RandomJokeViewState
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class RandomJokeViewStateIntegrationTest {

    open class DefaultDao : JokeDao {
        override fun getBookmarkedJokes() = Flowable.just(emptyList<JokeDBE>())
        override fun isBookmarked(id: String): Boolean = false
        override fun bookmarkJoke(joke: JokeDBE) = Completable.complete()
        override fun removeBookmarkedJoke(joke: JokeDBE) = Completable.complete()
    }

    private class TestModules(api: ChuckNorrisApi, dao: JokeDao = DefaultDao()) {
        val rxSchedulers = RxSchedulers(Schedulers.trampoline(), Schedulers.trampoline())

        private val jokeRepository = JokeRepositoryImpl(api, dao)

        val getRandomJokeUseCase: GetRandomJokeUseCase = GetRandomJoke(jokeRepository)
        val bookmarkJokeUseCase: BookmarkJokeUseCase = BookmarkJoke(jokeRepository)
        val getBookmarkedJokesUseCase: GetBookmarkedJokesUseCase =
            GetBookmarkedJokes(jokeRepository)
        val removeJokeFromBookmarksUseCase: RemoveJokeFromBookmarksUseCase =
            RemoveJokeFromBookmarks(jokeRepository)
    }

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @Test
    fun `given ChuckNorrisApi returns a joke, when loadInitialJoke() called, liveData should emit viewState Loaded`() {
        testViewStateLoaded(Joke(id = "1", value = "a", bookmarked = false))
    }

    @Test
    fun `given ChuckNorrisApi returns a joke that is already bookmarked, when loadInitialJoke() called, liveData should emit viewState Loaded with bookmarked joke`() {
        testViewStateLoaded(Joke(id = "1", value = "a", bookmarked = true))
    }

    @Test
    fun `given ChuckNorrisApi returns an error, when loadInitialJoke() called, liveData should emit viewState Error`() {
        val expectedErrorMessage = "Error"
        val expectedViewState = RandomJokeViewState.Error(expectedErrorMessage)

        val api = object : ChuckNorrisApi {
            override fun getRandomChuckNorrisJoke(): Single<JokeDTO> =
                Single.error(Throwable(expectedErrorMessage))
        }

        val viewModel = initViewModelWithTestModules(TestModules(api))

        viewModel.loadInitialJoke()

        val actualViewState = viewModel.viewState.value

        assertViewState(
            expectedViewState,
            actualViewState
        )
    }

    private fun testViewStateLoaded(expectedJoke: Joke) {
        val expectedViewState = RandomJokeViewState.Loaded(expectedJoke)

        val api = object : ChuckNorrisApi {
            override fun getRandomChuckNorrisJoke() =
                Single.just(expectedJoke.mapToDTO())

        }
        val dao = object : DefaultDao() {
            val bookmarkedJokes =
                if (expectedJoke.bookmarked) listOf(expectedJoke.mapToDBE()) else emptyList()

            override fun getBookmarkedJokes() = Flowable.just(bookmarkedJokes)
            override fun isBookmarked(id: String): Boolean =
                bookmarkedJokes.find { it.id == id } != null
        }

        val viewModel = initViewModelWithTestModules(TestModules(api, dao))
        viewModel.loadInitialJoke()

        val actualViewState = viewModel.viewState.value

        assertViewState(
            expectedViewState,
            actualViewState
        )
    }

    private fun initViewModelWithTestModules(modules: TestModules): RandomJokeViewModel {
        return RandomJokeViewModel(
            modules.getRandomJokeUseCase,
            modules.getBookmarkedJokesUseCase,
            modules.bookmarkJokeUseCase,
            modules.removeJokeFromBookmarksUseCase,
            modules.rxSchedulers
        )
    }

    private fun assertViewState(
        expectedViewState: RandomJokeViewState,
        actualViewState: RandomJokeViewState?,
    ) {
        assertEquals(expectedViewState.javaClass, actualViewState?.javaClass)
        if (expectedViewState is RandomJokeViewState.Error && actualViewState is RandomJokeViewState.Error) {
            assertEquals(expectedViewState.message, actualViewState.message)
        }
        if (expectedViewState is RandomJokeViewState.Loaded && actualViewState is RandomJokeViewState.Loaded) {
            assertEquals(expectedViewState.joke.id, actualViewState.joke.id)
            assertEquals(expectedViewState.joke.value, actualViewState.joke.value)
            assertEquals(expectedViewState.joke.bookmarked, actualViewState.joke.bookmarked)
        }

    }
}