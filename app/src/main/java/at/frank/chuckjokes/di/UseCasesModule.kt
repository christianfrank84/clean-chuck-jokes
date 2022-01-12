package at.frank.chuckjokes.di

import at.frank.chuckjokes.domain.*
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun providesGetRandomJokeUseCase(repository: JokeRepository): GetRandomJokeUseCase =
        GetRandomJoke(repository)

    @Provides
    fun providesGetBookmarkedJokesUseCase(repository: JokeRepository): GetBookmarkedJokesUseCase =
        GetBookmarkedJokes(repository)

    @Provides
    fun providesBookmarkJokeUseCase(repository: JokeRepository): BookmarkJokeUseCase =
        BookmarkJoke(repository)

    @Provides
    fun providesRemoveJokeFromBookmarksUseCase(repository: JokeRepository): RemoveJokeFromBookmarksUseCase =
        RemoveJokeFromBookmarks(repository)
}