package at.frank.chuckjokes.unittests.data.remote

import at.frank.chuckjokes.data.remote.ChuckNorrisApi
import at.frank.chuckjokes.data.remote.JokeDTO
import at.frank.chuckjokes.di.NetworkModule
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class ChuckNorrisApiTest {

    lateinit var jokeServer: MockWebServer
    lateinit var api: ChuckNorrisApi

    @Before
    fun setUp() {
        jokeServer = MockWebServer()
        jokeServer.start(8080)

        api = NetworkModule("http://${jokeServer.hostName}:${jokeServer.port}").providesChuckNorrisApi()
    }

    @Test
    fun `should return Joke if Response is a Json and has the correct keys`() {
        val jokeWebEntity = JokeDTO("http://some.url.at/", "someId", "some Joke", "01012000")
        val jokeJson = Gson().toJson(jokeWebEntity, JokeDTO::class.java)

        jokeServer.enqueue(MockResponse().setResponseCode(200).setBody(jokeJson))

        api.getRandomChuckNorrisJoke().test().assertComplete().assertValue(jokeWebEntity).dispose()
    }

    @Test
    fun `should return error if response is not a json`() {

        jokeServer.enqueue(MockResponse().setResponseCode(200).setBody(""))

        api.getRandomChuckNorrisJoke().test().assertNotComplete().dispose()
    }

    @Test
    fun `should return empty Joke if json is empty`() {
        jokeServer.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        api.getRandomChuckNorrisJoke().test().assertValue(JokeDTO()).dispose()
    }

    @After
    fun tearDown() {
        jokeServer.shutdown()
    }
}