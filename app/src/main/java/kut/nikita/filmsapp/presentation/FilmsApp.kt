package kut.nikita.filmsapp.presentation

import android.app.Application
import kut.nikita.filmsapp.data.FilmListRepository
import kut.nikita.filmsapp.domain.FilmListUseCase
import kut.nikita.filmsapp.domain.GetFilmUseCase

class FilmsApp : Application() {

    private val filmListRepository = FilmListRepository()

    val filmListUseCase = FilmListUseCase(filmListRepository)

    val getFilmUseCase = GetFilmUseCase(filmListRepository)
}