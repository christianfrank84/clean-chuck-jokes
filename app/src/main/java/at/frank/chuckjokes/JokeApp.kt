package at.frank.chuckjokes

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import at.frank.chuckjokes.data.JokeRepository
import at.frank.chuckjokes.data.JokeRepositoryImpl
import at.frank.chuckjokes.data.local.JokeDatabase
import at.frank.chuckjokes.data.remote.ChuckNorrisApi
import at.frank.chuckjokes.data.remote.RetroFitModule
import at.frank.chuckjokes.domain.BookmarkJokeUseCase
import at.frank.chuckjokes.domain.BookmarkJokeUseCaseImpl
import at.frank.chuckjokes.domain.GetRandomJoke
import at.frank.chuckjokes.domain.GetRandomJokeImpl
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class JokeApp : Application(), JokeAppContract {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            JokeDatabase::class.java, "jokeDb"
        ).build()
    }

    private val retrofit = RetroFitModule("https://api.chucknorris.io/").invoke()

    private val chuckNorrisApi: ChuckNorrisApi = retrofit.create(ChuckNorrisApi::class.java)

    private val repository: JokeRepository by lazy { JokeRepositoryImpl(chuckNorrisApi, db.jokeDao())}
    override val subscribeOn: Scheduler = Schedulers.io()
    override val observeOn: Scheduler = AndroidSchedulers.mainThread()

    override val getRandomJokeUseCase: GetRandomJoke by lazy { GetRandomJokeImpl(repository) }
    override val bookmarkJokeUseCase: BookmarkJokeUseCase by lazy {
        BookmarkJokeUseCaseImpl(
            repository
        )
    }

    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        super.onCreate()
    }

}