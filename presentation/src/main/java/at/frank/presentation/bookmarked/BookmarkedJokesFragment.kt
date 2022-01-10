package at.frank.presentation.bookmarked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import at.frank.domain.Joke
import at.frank.presentation.bookmarked.recyclerview.BookmarkedJokesRecyclerViewAdapter
import at.frank.presentation.bookmarked.recyclerview.DeleteJokeFromBookmarksListener
import at.frank.presentation.databinding.FragmentBookmarkedJokesBinding
import at.frank.presentation.getJokeApp
import at.frank.presentation.viewmodel.JokeViewModelFactory

class BookmarkedJokesFragment : Fragment(), DeleteJokeFromBookmarksListener {
    lateinit var viewModel: BookmarkedJokesViewModel

    private val adapter = BookmarkedJokesRecyclerViewAdapter(this)

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
        view.list.layoutManager = LinearLayoutManager(context)
        view.list.adapter = adapter

        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadBookmarkedJokes()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showError(view: FragmentBookmarkedJokesBinding, message: String) {
        TODO()
    }

    private fun showBookmarkedJokes(view: FragmentBookmarkedJokesBinding, jokes: List<Joke>) {
        adapter.setData(jokes)
    }

    private fun showLoadingIndicator(view: FragmentBookmarkedJokesBinding) {

    }

    override fun onDeleteJoke(joke: Joke) {
        viewModel.removeJokeFromBookmarks(joke)
    }


}