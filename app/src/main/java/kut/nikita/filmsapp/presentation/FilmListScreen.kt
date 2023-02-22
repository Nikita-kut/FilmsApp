package kut.nikita.filmsapp.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kut.nikita.filmsapp.R
import kut.nikita.filmsapp.domain.model.Film
import kut.nikita.filmsapp.presentation.components.RatingBar
import kut.nikita.filmsapp.presentation.viewmodel.FilmListViewModel

@Composable
fun FilmListScreen(
    filmListViewModel: FilmListViewModel = viewModel(factory = FilmListViewModel.FACTORY),
    onFilmClick: (String) -> Unit,
) {
    val chips: List<String> by filmListViewModel.chips.collectAsState(initial = emptyList())
    val films: List<Film> by filmListViewModel.films.collectAsState(initial = emptyList())
    val searchText: String by filmListViewModel.searchText.collectAsState(initial = "")
    val selectedChip: String? by filmListViewModel.selectedChip.collectAsState(initial = "")
    Column(
        Modifier
            .padding(dimensionResource(id = R.dimen.size_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.size_16))
    ) {
        Toolbar(
            chips,
            selectedChip,
            searchText,
            filmListViewModel::changeSearchText,
            filmListViewModel::clearSearchText,
            filmListViewModel::onChipClick,
        )
        FilmList(films, onFilmClick)
    }
}

@Composable
fun Toolbar(
    chipsList: List<String>,
    selectedChip: String?,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onClearIconClick: () -> Unit,
    onChipClick: (String?) -> Unit,
) {
    Column {
        SearchBar(
            searchText = searchText,
            onSearchTextChanged = onSearchTextChanged,
            onClearClick = onClearIconClick,
        )
        Text(
            text = stringResource(id = R.string.film_list_title),
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.size_22)),
            style = MaterialTheme.typography.subtitle2,
            fontSize = 16.sp
        )
        ChipsList(chipsList = chipsList, selectedChip = selectedChip, onChipClick)
    }
}

@Composable
fun ChipsList(chipsList: List<String>, selectedChip: String?, onChipClick: (String?) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.size_6))) {
        items(chipsList) { chip ->
            Chip(chip = chip, selectedChip = selectedChip, onChipClick)
        }
    }
}

@Composable
fun Chip(chip: String, selectedChip: String?, onChipClick: (String?) -> Unit) {
    TextButton(
        onClick = {
            selectedChip?.also {
                if (it != chip) onChipClick.invoke(chip) else onChipClick.invoke(null)
            } ?: onChipClick.invoke(chip)
        },
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.size_0)),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
    ) {
        Text(
            text = chip,
            fontSize = 10.sp,
            modifier = Modifier
                .border(
                    width = dimensionResource(id = R.dimen.size_1_5),
                    color = selectedChip?.let {
                        if (chip == selectedChip) Color.Red else Color.Black
                    } ?: Color.Black,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_10))
                )
                .padding(
                    horizontal = dimensionResource(id = R.dimen.size_9),
                    vertical = dimensionResource(id = R.dimen.size_4)
                )
        )
    }
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
        modifier = Modifier
            .size(
                width = dimensionResource(id = R.dimen.size_150),
                height = dimensionResource(id = R.dimen.size_365)
            )
            .clickable { onFilmClick(film.id) },
        elevation = dimensionResource(id = R.dimen.size_0),
    ) {
        Column() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("file:///android_asset/${film.imageName}")
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.size_216))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.size_10))),
                contentScale = ContentScale.Crop
            )
            Text(
                text = film.name,
                fontSize = 14.sp,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.size_8))
            )
            Text(
                text = film.description,
                fontSize = 12.sp,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.size_10),
                        bottom = dimensionResource(id = R.dimen.size_10)
                    )
                    .weight(1f),
            )
            RatingBar(rating = film.rating, maxStars = 5)
        }
    }
}