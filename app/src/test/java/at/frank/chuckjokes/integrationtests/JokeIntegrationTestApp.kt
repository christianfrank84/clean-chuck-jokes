package at.frank.chuckjokes.integrationtests

import at.frank.chuckjokes.data.local.JokeDao
import at.frank.chuckjokes.data.remote.ChuckNorrisApi
import at.frank.chuckjokes.domain.*
import at.frank.chuckjokes.presentation.JokeAppContract
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class JokeIntegrationTestApp(private val api: ChuckNorrisApi, private val dao: JokeDao) :
    JokeAppContract {
    override val subscribeOn: Scheduler = Schedulers.trampoline()
    override val observeOn: Scheduler = Schedulers.trampoline()

    private val jokeRepository = JokeRepositoryImpl(api, dao)

    override val getRandomJokeUseCase: GetRandomJokeUseCase = GetRandomJoke(jokeRepository)
    override val bookmarkJokeUseCase: BookmarkJokeUseCase = BookmarkJoke(jokeRepository)
    override val getBookmarkedJokesUseCase: GetBookmarkedJokesUseCase =
        GetBookmarkedJokes(jokeRepository)
    override val removeJokeFromBookmarksUseCase: RemoveJokeFromBookmarksUseCase =
        RemoveJokeFromBookmarks(jokeRepository)
}