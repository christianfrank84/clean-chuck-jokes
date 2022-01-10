package at.frank.presentation.randomjokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import at.frank.domain.Joke
import at.frank.presentation.R
import at.frank.presentation.databinding.FragmentJokeBinding
import at.frank.presentation.getJokeApp
import at.frank.presentation.viewmodel.JokeViewModelFactory

class RandomJokeFragment : Fragment() {
    lateinit var viewModel: RandomJokeViewModel

    var displayedJoke: Joke? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentJokeBinding.inflate(inflater, container, false)

        context?.let {
            val viewModelFactory = JokeViewModelFactory(it.getJokeApp())
            viewModel = ViewModelProvider(this, viewModelFactory)[RandomJokeViewModel::class.java]

            viewModel.jokeLiveData.observe(viewLifecycleOwner, { state ->
                when (state) {
                    is RandomJokeViewState.Loading -> showLoadingIndicator(view)
                    is RandomJokeViewState.Loaded -> showJoke(view, state.joke)
                    is RandomJokeViewState.Error -> showError(view, state.message)
                }
            })

            viewModel.toastLiveData.observe(viewLifecycleOwner, { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            })

            view.newJokeButton.setOnClickListener { viewModel.loadRandomJoke() }
            view.bookmarkButton.setOnClickListener {
                displayedJoke?.let { joke ->
                    if (joke.bookmarked)
                        viewModel.removeDisplayedJokeFromBookmarks()
                    else
                        viewModel.addDisplayedJokeToBookmarks()
                }


            }
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
        displayedJoke = joke
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