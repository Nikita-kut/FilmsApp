package kut.nikita.filmsapp.domain

import kut.nikita.filmsapp.data.FilmListRepository
import kut.nikita.filmsapp.data.FilmNotFoundException
import kut.nikita.filmsapp.data.mapper.toModel
import kut.nikita.filmsapp.domain.model.Film

class GetFilmUseCase(private val repository: FilmListRepository) {

    suspend fun getFilm(filmId: String): Film =
        repository.fetchFilmList().find { it.id == filmId }?.toModel()
            ?: throw FilmNotFoundException()
}