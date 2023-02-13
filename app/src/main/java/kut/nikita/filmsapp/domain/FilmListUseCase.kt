package kut.nikita.filmsapp.domain

import kut.nikita.filmsapp.data.FilmListRepository
import kut.nikita.filmsapp.data.mapper.toModel
import kut.nikita.filmsapp.domain.model.Film

class FilmListUseCase(private val repository: FilmListRepository) {

    suspend fun fetchChipsList(): List<String> = repository.fetchChipsList().map { it.lowercase() }

    suspend fun fetchFilmList(): List<Film> = repository.fetchFilmList().map { it.toModel() }
}