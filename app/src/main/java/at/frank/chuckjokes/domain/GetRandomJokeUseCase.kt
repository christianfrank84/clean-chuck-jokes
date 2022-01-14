package at.frank.chuckjokes.domain

import at.frank.chuckjokes.data.JokeRepository
import io.reactivex.Single
import javax.inject.Inject

interface GetRandomJokeUseCase {
    fun invoke(): Single<Joke>
}

class GetRandomJoke @Inject constructor(private val repo: JokeRepository) : GetRandomJokeUseCase {
    override fun invoke(): Single<Joke> = repo.getRandomJoke()
        .map { Joke.mapFromDTO(it) }
        .map {
            repo.isJokeBookmarked(it.id).let { isBookmarked ->
                it.bookmarked = isBookmarked
                it
            }
        }
}
