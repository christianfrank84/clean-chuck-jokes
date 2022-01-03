package at.frank.chuckjokes.data

import at.frank.chuckjokes.domain.Joke
import io.reactivex.Observable
import retrofit2.http.GET

interface ChuckNorrisApi {
    @GET("jokes/random")
    fun getRandomChuckNorrisJoke(): Observable<Joke>
}
