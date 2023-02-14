package kut.nikita.filmsapp.data.mapper

import kut.nikita.filmsapp.data.model.FilmDto
import kut.nikita.filmsapp.domain.model.Film

fun FilmDto.toModel(): Film = Film(
    id = id,
    name = name,
    phone = phone,
    datePublication = date_publication,
    rating = rating,
    description = description
)

fun Film.toDto(): FilmDto = FilmDto(
    id = id,
    name = name,
    phone = phone,
    date_publication = datePublication,
    rating = rating,
    description = description
)