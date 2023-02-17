package kut.nikita.filmsapp.data

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kut.nikita.filmsapp.data.model.FilmDto
import okio.IOException

class FilmListRepository(private val appContext: Context) {

    suspend fun fetchChipsList(): List<String> = getDataFromAssetsJson("Chips.json")

    suspend fun fetchFilmList(): List<FilmDto> = getDataFromAssetsJson("FilmList.json")

    private inline fun <reified T> getDataFromAssetsJson(fileName: String): T =
        try {
            appContext.assets.open(fileName)
                .bufferedReader()
                .use { it.readText() }.let { jsonString ->
                    Json.decodeFromString(jsonString)
                }
        } catch (e: Exception) {
            throw IOException()
        }
}