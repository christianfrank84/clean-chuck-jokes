package at.frank.chuckjokes.unittests.di

import at.frank.chuckjokes.RxSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers

@Module
class TestAppModule {

    @Provides
    fun provideRxSchedulers(): RxSchedulers =
        RxSchedulers(Schedulers.trampoline(), Schedulers.trampoline())
}