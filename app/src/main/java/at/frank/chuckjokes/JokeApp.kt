package at.frank.chuckjokes

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import at.frank.chuckjokes.data.ChuckNorrisApi
import at.frank.chuckjokes.data.JokeRepository
import at.frank.chuckjokes.data.JokeRepositoryImpl
import at.frank.chuckjokes.domain.GetRandomJoke
import at.frank.chuckjokes.domain.GetRandomJokeImpl
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class JokeApp : Application(), JokeAppContract {

    private val retrofit = RetroFitModule("https://api.chucknorris.io/").invoke()

    private val chuckNorrisApi: ChuckNorrisApi = retrofit.create(ChuckNorrisApi::class.java)

    private val repository: JokeRepository = JokeRepositoryImpl(chuckNorrisApi)
    override val subscribeOn: Scheduler = Schedulers.io()
    override val observeOn: Scheduler = AndroidSchedulers.mainThread()

    override val getRandomJokeUseCase: GetRandomJoke = GetRandomJokeImpl(repository)

    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        super.onCreate()
    }

}