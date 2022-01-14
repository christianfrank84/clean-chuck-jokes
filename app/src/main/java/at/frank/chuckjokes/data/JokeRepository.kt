package at.frank.chuckjokes.data

import at.frank.chuckjokes.data.local.JokeDBE
import at.frank.chuckjokes.data.local.JokeDao
import at.frank.chuckjokes.data.remote.ChuckNorrisApi
import at.frank.chuckjokes.data.remote.JokeDTO
import at.frank.chuckjokes.domain.Joke
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

interface JokeRepository {
    fun getRandomJoke(): Single<JokeDTO>
    fun getBookmarkedJokes(): Flowable<List<JokeDBE>>
    fun isJokeBookmarked(id: String): Boolean
    fun bookmarkJoke(entity: JokeDBE): Completable
    fun removeBookmarkedJoke(entity: JokeDBE): Completable
}

@Singleton
class JokeRepositoryImpl @Inject constructor(
    private val api: ChuckNorrisApi,
    private val jokeDao: JokeDao
) : JokeRepository {
    override fun getRandomJoke(): Single<JokeDTO> = api.getRandomChuckNorrisJoke()
    override fun getBookmarkedJokes(): Flowable<List<JokeDBE>> = jokeDao.getBookmarkedJokes()
    override fun isJokeBookmarked(id: String): Boolean = jokeDao.isBookmarked(id)
    override fun bookmarkJoke(entity: JokeDBE) = jokeDao.bookmarkJoke(entity)
    override fun removeBookmarkedJoke(entity: JokeDBE) = jokeDao.removeBookmarkedJoke(entity)
}
