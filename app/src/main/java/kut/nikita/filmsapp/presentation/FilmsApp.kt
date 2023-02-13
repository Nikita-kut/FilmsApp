package kut.nikita.filmsapp.presentation

import android.app.Application
import kut.nikita.filmsapp.data.FilmListRepository
import kut.nikita.filmsapp.domain.FilmListUseCase

class FilmsApp : Application() {

    val filmListUseCase = FilmListUseCase(FilmListRepository())
}