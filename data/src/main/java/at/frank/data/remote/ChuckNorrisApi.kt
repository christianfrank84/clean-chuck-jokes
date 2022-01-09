package at.frank.data.remote

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface ChuckNorrisApi {
    @GET("jokes/random")
    fun getRandomChuckNorrisJoke(): Single<JokeDTO>
}
