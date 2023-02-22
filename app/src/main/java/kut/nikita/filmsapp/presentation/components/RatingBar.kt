package kut.nikita.filmsapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import kut.nikita.filmsapp.R

@Composable
fun RatingBar(rating: Int, maxStars: Int) {
    val unFilledStars = maxStars - rating
    Row(Modifier.height(dimensionResource(id = R.dimen.size_14))) {
        repeat(rating) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "",
                tint = Color.Black,
            )
        }
        repeat(unFilledStars) {
            Icon(
                imageVector = Icons.Filled.StarOutline,
                contentDescription = "",
                tint = Color.Black
            )
        }
    }
}