package at.frank.chuckjokes.domain

import at.frank.chuckjokes.data.local.JokeDao
import at.frank.chuckjokes.data.remote.ChuckNorrisApi
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface JokeRepository {
    fun getRandomJoke(): Single<Joke>
    fun getBookmarkedJokes(): Flowable<List<Joke>>
    fun bookmarkJoke(joke: Joke): Completable
    fun removeBookmarkedJoke(joke: Joke): Completable
}

class JokeRepositoryImpl(private val api: ChuckNorrisApi, private val jokeDao: JokeDao) :
    JokeRepository {
    override fun getRandomJoke(): Single<Joke> {
        return api.getRandomChuckNorrisJoke().flatMap { jokeDTO ->
            Single.create {
                val bookmarked = jokeDao.isBookmarked(jokeDTO.id)
                val joke = Joke.mapFromDTO(jokeDTO).apply { this.bookmarked = bookmarked }
                if (!it.isDisposed) {
                    it.onSuccess(joke)
                }
            }
        }
    }

    override fun getBookmarkedJokes(): Flowable<List<Joke>> =
        jokeDao.getBookmarkedJokes().map { list ->
            list.map { jokeDBE -> Joke.mapFromDBE(jokeDBE) }
        }

    override fun bookmarkJoke(joke: Joke) =
        jokeDao.bookmarkJoke(joke.mapToDBE())

    override fun removeBookmarkedJoke(joke: Joke) =
        jokeDao.removeBookmarkedJoke(joke.mapToDBE())
}
