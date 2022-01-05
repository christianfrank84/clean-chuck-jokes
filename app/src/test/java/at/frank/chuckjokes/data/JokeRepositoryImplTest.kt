package at.frank.chuckjokes.data

import at.frank.chuckjokes.data.remote.ChuckNorrisApi
import at.frank.chuckjokes.data.remote.JokeWebEntity
import at.frank.chuckjokes.data.remote.RetroFitModule
import at.frank.chuckjokes.domain.Joke
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class JokeRepositoryImplTest {

    lateinit var jokeServer: MockWebServer
    lateinit var api: ChuckNorrisApi

    @Before
    fun setUp() {
        jokeServer = MockWebServer()
        jokeServer.start(8080)

        api = RetroFitModule("http://${jokeServer.hostName}:${jokeServer.port}").invoke()
            .create(ChuckNorrisApi::class.java)
    }

    @Test
    fun `should return Joke if Response is a Json and has the correct keys`() {
        val jokeRepositoryImpl = JokeRepositoryImpl(api)

        val jokeWebEntity = JokeWebEntity("http://some.url.at/", "someId", "some Joke", "01012000")
        val jokeJson = Gson().toJson(jokeWebEntity, JokeWebEntity::class.java)

        val expectedJokeDomainEntity = Joke(iconUrl = jokeWebEntity.iconUrl, id = jokeWebEntity.id, createdAt = jokeWebEntity.createdAt, value = jokeWebEntity.value )

        jokeServer.enqueue(MockResponse().setResponseCode(200).setBody(jokeJson))

        jokeRepositoryImpl.getRandomJoke().test().assertComplete().assertValue(expectedJokeDomainEntity).dispose()
    }

    @Test
    fun `should return error if response is not a json`() {
        val jokeRepositoryImpl = JokeRepositoryImpl(api)
        jokeServer.enqueue(MockResponse().setResponseCode(200).setBody(""))

        jokeRepositoryImpl.getRandomJoke().test().assertNotComplete().dispose()
    }

    @Test
    fun `should return empty Joke if json is empty`() {
        val jokeRepositoryImpl = JokeRepositoryImpl(api)
        jokeServer.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        jokeRepositoryImpl.getRandomJoke().test().assertValue(Joke()).dispose()
    }

    @After
    fun tearDown() {
        jokeServer.shutdown()
    }
}