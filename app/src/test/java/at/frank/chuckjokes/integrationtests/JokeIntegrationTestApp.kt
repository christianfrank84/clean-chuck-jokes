package at.frank.chuckjokes.integrationtests

import at.frank.data.local.JokeDao
import at.frank.data.remote.ChuckNorrisApi
import at.frank.domain.*
import at.frank.presentation.JokeAppContract
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class JokeIntegrationTestApp(private val api: ChuckNorrisApi, private val dao: JokeDao): JokeAppContract {
    override val subscribeOn: Scheduler = Schedulers.trampoline()
    override val observeOn: Scheduler = Schedulers.trampoline()

    private val jokeRepository = JokeRepositoryImpl(api, dao)

    override val getRandomJokeUseCase: GetRandomJokeUseCase = GetRandomJoke(jokeRepository)
    override val bookmarkJokeUseCase: BookmarkJokeUseCase = BookmarkJoke(jokeRepository)
    override val getBookmarkedJokesUseCase: GetBookmarkedJokesUseCase = GetBookmarkedJokes(jokeRepository)
    override val removeJokeFromBookmarksUseCase: RemoveJokeFromBookmarksUseCase = RemoveJokeFromBookmarks(jokeRepository)
}