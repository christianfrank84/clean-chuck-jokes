package at.frank.chuckjokes.domain

import at.frank.chuckjokes.data.JokeRepository
import io.reactivex.Flowable
import javax.inject.Inject

interface GetBookmarkedJokesUseCase {
    fun invoke(): Flowable<List<Joke>>
}

class GetBookmarkedJokes @Inject constructor(private val repo: JokeRepository) :
    GetBookmarkedJokesUseCase {
    override fun invoke(): Flowable<List<Joke>> = repo.getBookmarkedJokes()
        .map {
            it.map { dbe -> Joke.mapFromDBE(dbe) }
        }
}
