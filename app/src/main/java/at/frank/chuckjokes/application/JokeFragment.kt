package at.frank.chuckjokes.application

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import at.frank.chuckjokes.databinding.FragmentJokeBinding
import at.frank.chuckjokes.domain.GetRandomJoke
import at.frank.chuckjokes.domain.Joke
import at.frank.chuckjokes.getJokeApp
import at.frank.chuckjokes.presentation.JokeViewModel
import at.frank.chuckjokes.presentation.JokeViewModelFactory
import at.frank.chuckjokes.presentation.JokeViewState

class JokeFragment : Fragment() {

    lateinit var getRandomJoke: GetRandomJoke
    lateinit var viewModel: JokeViewModel

    override fun onAttach(context: Context) {
        getRandomJoke = context.getJokeApp().getRandomJokeUseCase
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentJokeBinding.inflate(inflater, container, false)

        context?.let {
            val viewModelFactory = JokeViewModelFactory(it.getJokeApp())
            viewModel = ViewModelProvider(this, viewModelFactory)[JokeViewModel::class.java]

            viewModel.jokeLiveData.observe(viewLifecycleOwner, { state ->
                when (state) {
                    is JokeViewState.Loading -> showLoadingIndicator(view)
                    is JokeViewState.Loaded -> showJoke(view, state.joke)
                    is JokeViewState.Error -> showError(view, state.message)
                }
            })
        }

        return view.root
    }

    private fun showError(view: FragmentJokeBinding, message: String) {
        view.jokeText.text = message
    }

    private fun showJoke(view: FragmentJokeBinding, joke: Joke) {
       view.jokeText.text = joke.value
    }

    private fun showLoadingIndicator(view: FragmentJokeBinding) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadRandomJoke()
        super.onViewCreated(view, savedInstanceState)
    }
}