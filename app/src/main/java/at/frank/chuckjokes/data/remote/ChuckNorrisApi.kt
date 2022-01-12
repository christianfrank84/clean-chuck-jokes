package at.frank.chuckjokes.data.remote

import io.reactivex.Single
import retrofit2.http.GET

interface ChuckNorrisApi {
    @GET("jokes/random")
    fun getRandomChuckNorrisJoke(): Single<JokeDTO>
}
