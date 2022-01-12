package at.frank.chuckjokes.domain

import io.reactivex.Flowable

interface GetBookmarkedJokesUseCase {
    fun invoke(): Flowable<List<Joke>>
}

class GetBookmarkedJokes(private val repo: JokeRepository) : GetBookmarkedJokesUseCase {
    override fun invoke(): Flowable<List<Joke>> = repo.getBookmarkedJokes()
}
