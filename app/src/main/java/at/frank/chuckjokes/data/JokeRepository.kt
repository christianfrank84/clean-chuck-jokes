package at.frank.chuckjokes.data

import at.frank.chuckjokes.data.local.JokeDBEntity
import at.frank.chuckjokes.data.local.JokeDao
import at.frank.chuckjokes.data.remote.ChuckNorrisApi
import at.frank.chuckjokes.data.remote.JokeWebEntity
import at.frank.chuckjokes.domain.Joke
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function

interface JokeRepository {
    fun getRandomJoke(): Observable<Joke>
    fun getBookmarkedJokes(): Single<List<Joke>>
    fun bookmarkJoke(joke: Joke): Completable
    fun removeBookmarkedJoke(joke: Joke): Completable
}

class JokeRepositoryImpl(private val api: ChuckNorrisApi, private val jokeDao: JokeDao) :
    JokeRepository {
    override fun getRandomJoke(): Observable<Joke> =
        api.getRandomChuckNorrisJoke().map(JokeWebToDomainMapperFunction())

    override fun getBookmarkedJokes(): Single<List<Joke>> =
        jokeDao.getBookmarkedJokes().map { list ->
            list.map { joke -> JokeDbToDomainMapperFunction().apply(joke) }
        }

    override fun bookmarkJoke(joke: Joke) =
        jokeDao.bookmarkJoke(JokeDomainToDbMapperFunction().apply(joke))

    override fun removeBookmarkedJoke(joke: Joke) =
        jokeDao.removeBookmarkedJoke(JokeDomainToDbMapperFunction().apply(joke))


}

class JokeWebToDomainMapperFunction : Function<JokeWebEntity, Joke> {
    override fun apply(t: JokeWebEntity): Joke {
        return Joke(iconUrl = t.iconUrl, id = t.id, value = t.value, createdAt = t.createdAt)
    }
}

class JokeDomainToDbMapperFunction : Function<Joke, JokeDBEntity> {
    override fun apply(t: Joke): JokeDBEntity {
        return JokeDBEntity(
            id = t.id,
            iconUrl = t.iconUrl,
            value = t.value,
            createdAt = t.createdAt,
            isBookmarked = t.isBookmarked
        )
    }
}

class JokeDbToDomainMapperFunction : Function<JokeDBEntity, Joke> {
    override fun apply(t: JokeDBEntity): Joke {
        return Joke(
            iconUrl = t.iconUrl,
            id = t.id,
            value = t.value,
            createdAt = t.createdAt
        )
    }
}
