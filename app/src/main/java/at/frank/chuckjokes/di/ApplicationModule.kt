package at.frank.chuckjokes.di

import android.app.Application
import at.frank.chuckjokes.RxSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class ApplicationModule() {

    @Provides
    fun provideRxSchedulers(): RxSchedulers =
        RxSchedulers(Schedulers.io(), AndroidSchedulers.mainThread())


}