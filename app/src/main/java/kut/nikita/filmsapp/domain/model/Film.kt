package kut.nikita.filmsapp.domain.model

data class Film(
    val id: String,
    val name: String,
    val phone: String,
    val datePublication: String,
    val rating: Int,
    val description: String,
    val genre: String,
    val imageName: String,
)