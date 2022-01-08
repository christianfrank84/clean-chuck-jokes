package at.frank.domain

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface JokeRepository {
    fun getRandomJoke(): Observable<Joke>
    fun getBookmarkedJokes(): Single<List<Joke>>
    fun bookmarkJoke(joke: Joke): Completable
    fun removeBookmarkedJoke(joke: Joke): Completable
}

class JokeRepositoryImpl(private val api: ChuckNorrisApi, private val jokeDao: JokeDao) :
    JokeRepository {
    override fun getRandomJoke(): Observable<Joke> =
        api.getRandomChuckNorrisJoke().map { Joke.mapFromDTO(it) }

    override fun getBookmarkedJokes(): Single<List<Joke>> =
        jokeDao.getBookmarkedJokes().map { list ->
            list.map { jokeDBE -> Joke.mapFromDBE(jokeDBE) }
        }

    override fun bookmarkJoke(joke: Joke) =
        jokeDao.bookmarkJoke(joke.mapToDBE())

    override fun removeBookmarkedJoke(joke: Joke) =
        jokeDao.removeBookmarkedJoke(joke.mapToDBE())
}
