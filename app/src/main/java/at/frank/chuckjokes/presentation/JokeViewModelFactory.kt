package at.frank.chuckjokes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.frank.chuckjokes.JokeAppContract

class JokeViewModelFactory(private val app: JokeAppContract): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JokeViewModel(app) as T
    }
}