package at.frank.chuckjokes.di

import at.frank.chuckjokes.RxSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class ApplicationModule {

    @Provides
    fun provideRxSchedulers(): RxSchedulers =
        RxSchedulers(Schedulers.io(), AndroidSchedulers.mainThread())


}