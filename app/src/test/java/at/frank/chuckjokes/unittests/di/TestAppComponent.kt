package at.frank.chuckjokes.unittests.di

import at.frank.chuckjokes.di.*
import at.frank.chuckjokes.unittests.data.remote.ChuckNorrisApiTest
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        TestAppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UseCasesModule::class]
)
interface TestAppComponent : ApplicationComponent {
    fun inject(test: ChuckNorrisApiTest)
}

//TODO: introduce TestRunner
//https://medium.com/swlh/unit-testing-with-dagger-2-brewing-a-potion-with-fakes-mocks-and-custom-rules-in-android-7f0ab7b22cb
