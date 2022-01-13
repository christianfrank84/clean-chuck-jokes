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
import javax.inject.Inject

class RandomJokeFragment : Fragment() {

    lateinit var viewModel: RandomJokeViewModel

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

    override fun onAttach(context: Context) {
        (context.applicationContext as JokeApp).applicationComponent.injectRandomJokeFragment(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentJokeBinding.inflate(inflater, container, false)

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
                    is RandomJokeViewState.Loading -> showLoadingIndicator(view)
                    is RandomJokeViewState.Loaded -> showJoke(view, state.joke)
                    is RandomJokeViewState.Error -> showError(view, state.message)
                }
            })

            view.newJokeButton.setOnClickListener { viewModel.loadRandomJoke() }
            view.bookmarkButton.setOnClickListener { viewModel.bookmarkPressed() }
        }

        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadInitialJoke()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showError(view: FragmentJokeBinding, message: String) {
        view.jokeText.text = message
    }

    private fun showJoke(view: FragmentJokeBinding, joke: Joke) {
        view.jokeText.text = joke.value
        view.bookmarkButton.setImageResource(
            if (joke.bookmarked)
                R.drawable.ic_bookmarked
            else
                R.drawable.ic_bookmark
        )
    }

    private fun showLoadingIndicator(view: FragmentJokeBinding) {

    }
}