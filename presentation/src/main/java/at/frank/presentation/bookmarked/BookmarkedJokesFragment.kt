package at.frank.presentation.bookmarked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import at.frank.domain.Joke
import at.frank.presentation.databinding.FragmentBookmarkedJokesBinding
import at.frank.presentation.getJokeApp
import at.frank.presentation.viewmodel.JokeViewModelFactory

class BookmarkedJokesFragment : Fragment() {
    lateinit var viewModel: BookmarkedJokesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentBookmarkedJokesBinding.inflate(inflater, container, false)

        context?.let {
            val viewModelFactory = JokeViewModelFactory(it.getJokeApp())
            viewModel =
                ViewModelProvider(this, viewModelFactory)[BookmarkedJokesViewModel::class.java]

            viewModel.viewState.observe(viewLifecycleOwner, { state ->
                when (state) {
                    is BookmarkedJokesViewState.Loading -> showLoadingIndicator(view)
                    is BookmarkedJokesViewState.Loaded -> showBookmarkedJokes(view, state.jokes)
                    is BookmarkedJokesViewState.Error -> showError(view, state.message)
                }
            })
        }

        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadInitialJoke()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showError(view: FragmentBookmarkedJokesBinding, message: String) {
        TODO()
    }

    private fun showBookmarkedJokes(view: FragmentBookmarkedJokesBinding, jokes: List<Joke>) {
        TODO()
    }

    private fun showLoadingIndicator(view: FragmentBookmarkedJokesBinding) {

    }


}