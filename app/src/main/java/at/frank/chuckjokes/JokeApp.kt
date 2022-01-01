package at.frank.chuckjokes

import android.app.Application
import at.frank.chuckjokes.data.GetRandomJoke
import at.frank.chuckjokes.data.GetRandomJokeImpl
import at.frank.chuckjokes.domain.ChuckNorrisApi
import at.frank.chuckjokes.domain.JokeRepository
import at.frank.chuckjokes.domain.JokeRepositoryImpl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


class JokeApp : Application() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.chucknorris.io/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build()

    private val chuckNorrisApi: ChuckNorrisApi = retrofit.create(ChuckNorrisApi::class.java)

    private val repository: JokeRepository = JokeRepositoryImpl(chuckNorrisApi)

    val getRandomJokeUseCase: GetRandomJoke = GetRandomJokeImpl(repository)
}