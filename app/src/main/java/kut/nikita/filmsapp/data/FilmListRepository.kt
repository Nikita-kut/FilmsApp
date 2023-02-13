package kut.nikita.filmsapp.data

import kut.nikita.filmsapp.data.model.FilmDto

class FilmListRepository {

    suspend fun fetchChipsList(): List<String> =
        listOf("Боевики", "Драмы", "Комедии", "Артхаус", "Мелодрамы")

    suspend fun fetchFilmList(): List<FilmDto> = listOf(
        FilmDto(
            id = "1",
            name = "Гнев человеческий",
            phone = "",
            date_publication = "",
            rating = 3,
            description = "Фильм про Джейсона Стетхема крутого",
        ),
        FilmDto(
            id = "2",
            name = "Мортал комбат",
            phone = "",
            date_publication = "",
            rating = 5,
            description = "Сабзиро и Скорпион дерутся",
        ),
        FilmDto(
            id = "3",
            name = "Упс... Приплыли",
            phone = "",
            date_publication = "",
            rating = 4,
            description = "Какие-то зверушки плывут на бочке",
        ),
        FilmDto(
            id = "4",
            name = "The Box",
            phone = "",
            date_publication = "",
            rating = 2,
            description = "Женщина поёт в микрофон 10 часов",
        ),
    )
}