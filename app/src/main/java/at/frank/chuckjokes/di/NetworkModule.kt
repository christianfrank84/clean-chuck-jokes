package at.frank.chuckjokes.di

import at.frank.chuckjokes.data.remote.ChuckNorrisApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val endpoint: String) {

    @Singleton
    @Provides
    fun providesChuckNorrisApi(): ChuckNorrisApi {
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(ChuckNorrisApi::class.java)
    }
}

