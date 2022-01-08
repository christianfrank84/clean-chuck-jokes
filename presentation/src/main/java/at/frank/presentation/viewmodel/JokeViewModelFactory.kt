package at.frank.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.frank.presentation.JokeAppContract

class JokeViewModelFactory(private val app: JokeAppContract): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JokeViewModel(app) as T
    }
}