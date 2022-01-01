package at.frank.chuckjokes.domain

import at.frank.chuckjokes.data.Joke
import io.reactivex.Observable
import retrofit2.http.GET

interface ChuckNorrisApi {
    @GET("jokes/random")
    fun getRandomChuckNorrisJoke(): Observable<Joke>
}
