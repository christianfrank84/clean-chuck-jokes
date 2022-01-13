package at.frank.chuckjokes.presentation.randomjokes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import at.frank.chuckjokes.JokeApp
import at.frank.chuckjokes.R
import at.frank.chuckjokes.RxSchedulers
import at.frank.chuckjokes.databinding.FragmentJokeBinding
import at.frank.chuckjokes.domain.*
import at.frank.chuckjokes.presentation.viewmodel.JokeViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RandomJokeFragment : DaggerFragment() {

    private lateinit var viewModel: RandomJokeViewModel
    private lateinit var binding: FragmentJokeBinding

    @Inject
    lateinit var getBookmarkedJokesUseCase: GetBookmarkedJokesUseCase

    @Inject
    lateinit var getRandomJokeUseCase: GetRandomJokeUseCase

    @Inject
    lateinit var bookmarkJokeUseCase: BookmarkJokeUseCase

    @Inject
    lateinit var removeJokeFromBookmarksUseCase: RemoveJokeFromBookmarksUseCase

    @Inject
    lateinit var rxSchedulers: RxSchedulers

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJokeBinding.inflate(inflater, container, false)

        context?.let {
            val viewModelFactory = JokeViewModelFactory(
                getBookmarkedJokesUseCase,
                getRandomJokeUseCase,
                bookmarkJokeUseCase,
                removeJokeFromBookmarksUseCase,
                rxSchedulers
            )

            viewModel = ViewModelProvider(this, viewModelFactory)[RandomJokeViewModel::class.java]

            viewModel.viewState.observe(viewLifecycleOwner, { state ->
                when (state) {
                    is RandomJokeViewState.Loading -> showLoadingIndicator()
                    is RandomJokeViewState.Loaded -> showJoke(state.joke)
                    is RandomJokeViewState.Error -> showError(state.message)
                }
            })

            binding.newJokeButton.setOnClickListener { viewModel.loadRandomJoke() }
            binding.bookmarkButton.setOnClickListener { viewModel.bookmarkPressed() }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadInitialJoke()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showError(message: String) {
        binding.jokeText.text = message
    }

    private fun showJoke(joke: Joke) {
        binding.jokeText.text = joke.value
        binding.bookmarkButton.setImageResource(
            if (joke.bookmarked)
                R.drawable.ic_bookmarked
            else
                R.drawable.ic_bookmark
        )
    }

    private fun showLoadingIndicator() {

    }
}