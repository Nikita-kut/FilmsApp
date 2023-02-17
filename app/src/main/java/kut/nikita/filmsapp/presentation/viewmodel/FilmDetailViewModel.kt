package kut.nikita.filmsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kut.nikita.filmsapp.data.FilmNotFoundException
import kut.nikita.filmsapp.domain.GetFilmUseCase
import kut.nikita.filmsapp.domain.model.Film
import kut.nikita.filmsapp.presentation.FilmsApp

class FilmDetailViewModel(
    private val getFilmUseCase: GetFilmUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val film: Flow<Film> get() = _film
    private val _film =
        MutableSharedFlow<Film>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    private val filmId: String = checkNotNull(savedStateHandle["id"])

    init {
        viewModelScope.launch {
            try {
                _film.emit(getFilmUseCase.getFilm(filmId))
            } catch (e: FilmNotFoundException) {
                // TODO обработать ошибку
                Log.e(TAG, "Фильм не найден")
            }
        }
    }

    companion object {
        private const val TAG = "FilmDetailVM"

        val FACTORY = viewModelFactory {
            initializer {
                val savedState = createSavedStateHandle()
                val useCase =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FilmsApp).getFilmUseCase
                FilmDetailViewModel(useCase, savedState)
            }
        }

        val EMPTY_FILM = Film(
            id = "",
            name = "",
            phone = "",
            datePublication = "",
            rating = 0,
            description = ""
        )
    }
}