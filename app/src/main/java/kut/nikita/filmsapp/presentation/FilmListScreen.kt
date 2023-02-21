package kut.nikita.filmsapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kut.nikita.filmsapp.R
import kut.nikita.filmsapp.domain.model.Film
import kut.nikita.filmsapp.presentation.viewmodel.FilmListViewModel
import kut.nikita.filmsapp.ui.theme.FilmsAppTheme

@Composable
fun FilmListScreen(
    filmListViewModel: FilmListViewModel = viewModel(factory = FilmListViewModel.FACTORY),
    onFilmClick: (String) -> Unit,
) {
    val chips: List<String> by filmListViewModel.chips.collectAsState(initial = emptyList())
    val films: List<Film> by filmListViewModel.films.collectAsState(initial = emptyList())
    Column(
        Modifier.padding(dimensionResource(id = R.dimen.size_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.size_16))
    ) {
        Toolbar(chips)
        FilmList(films, onFilmClick)
    }
}

@Composable
fun Toolbar(chipsList: List<String>) {
    Column() {
        Row() {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "",
                Modifier.size(dimensionResource(id = R.dimen.size_20))
            )
        }
        Text(
            text = stringResource(id = R.string.film_list_title),
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.size_22)),
            style = MaterialTheme.typography.subtitle2,
            fontSize = dimensionResource(id = R.dimen.text_size_16).value.sp
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.size_6))) {
            items(chipsList) { chip ->
                Chip(chip = chip)
            }
        }
    }
}

@Composable
fun Chip(chip: String) {
    Text(
        text = chip,
        fontSize = dimensionResource(id = R.dimen.text_size_10).value.sp,
        modifier = Modifier
            .border(
                width = dimensionResource(id = R.dimen.size_1_5),
                color = Color.Black,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_10))
            )
            .padding(
                horizontal = dimensionResource(id = R.dimen.size_9),
                vertical = dimensionResource(id = R.dimen.size_4)
            )
    )
}

@Composable
fun FilmList(filmList: List<Film>, onFilmClick: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.size_20)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.size_50)),
        content = {
            items(filmList) { film ->
                FilmCard(film, onFilmClick)
            }
        }
    )
}

@Composable
fun FilmCard(film: Film, onFilmClick: (String) -> Unit) {
    Card(
        modifier = Modifier.size(
            width = dimensionResource(id = R.dimen.size_150),
            height = dimensionResource(id = R.dimen.size_365)
        )
            .clickable { onFilmClick(film.id) }
    ) {
        Column() {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.size_216))
                    .border(
                        width = dimensionResource(id = R.dimen.size_0),
                        color = Color.Black,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_10))
                    ),
                contentScale = ContentScale.Crop
            )
            Text(
                text = film.name,
                fontSize = dimensionResource(id = R.dimen.text_size_14).value.sp,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.size_8))
            )
            Text(
                text = film.description,
                fontSize = dimensionResource(id = R.dimen.text_size_12).value.sp,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.size_10))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FilmsAppTheme {
        FilmListScreen {}
    }
}