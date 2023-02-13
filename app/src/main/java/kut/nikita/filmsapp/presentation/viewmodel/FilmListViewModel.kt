package kut.nikita.filmsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kut.nikita.filmsapp.domain.FilmListUseCase
import kut.nikita.filmsapp.domain.model.Film
import kut.nikita.filmsapp.presentation.FilmsApp

class FilmListViewModel(
    private val filmListUseCase: FilmListUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val chips: Flow<List<String>> get() = _chips
    private val _chips = MutableSharedFlow<List<String>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    val films: Flow<List<Film>> get() = _films
    private val _films = MutableSharedFlow<List<Film>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, exception.stackTraceToString())
    }

    private val loadJobs = listOf(
        viewModelScope.launch {
            _chips.emit(filmListUseCase.fetchChipsList())
        },
        viewModelScope.launch {
            _films.emit(filmListUseCase.fetchFilmList())
        }
    )

    init {
        viewModelScope.launch(exceptionHandler) {
            loadJobs.joinAll()
        }
    }

    companion object {
        private const val TAG = "FilmListVM"

        val FACTORY = viewModelFactory {
            initializer {
                val savedState = createSavedStateHandle()
                val useCase = (this[APPLICATION_KEY] as FilmsApp).filmListUseCase
                FilmListViewModel(useCase, savedState)
            }
        }
    }
}