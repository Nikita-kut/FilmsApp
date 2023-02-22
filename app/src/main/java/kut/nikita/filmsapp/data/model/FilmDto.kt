package kut.nikita.filmsapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("date_publication")
    val date_publication: String,
    @SerialName("rating")
    val rating: Int,
    @SerialName("description")
    val description: String,
    @SerialName("genre")
    val genre: String,
    @SerialName("image_name")
    val image_name: String,
)