package at.frank.chuckjokes.data

import at.frank.chuckjokes.data.remote.ChuckNorrisApi
import at.frank.chuckjokes.data.remote.JokeWebEntity
import at.frank.chuckjokes.domain.Joke
import io.reactivex.Observable
import io.reactivex.functions.Function

interface JokeRepository {
    fun getRandomJoke(): Observable<Joke>
}

class JokeRepositoryImpl(private val api: ChuckNorrisApi) : JokeRepository {
    override fun getRandomJoke(): Observable<Joke> = api.getRandomChuckNorrisJoke().map(JokeWebToDomainMapperFunction())
}

class JokeWebToDomainMapperFunction: Function<JokeWebEntity, Joke> {
    override fun apply(t: JokeWebEntity): Joke {
        return Joke(iconUrl = t.iconUrl, id = t.id, value = t.value, createdAt = t.createdAt)
    }
}
