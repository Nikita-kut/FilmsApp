package kut.nikita.filmsapp.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import kut.nikita.filmsapp.domain.model.Film
import kut.nikita.filmsapp.presentation.viewmodel.FilmDetailViewModel
import kut.nikita.filmsapp.presentation.viewmodel.FilmDetailViewModel.Companion.EMPTY_FILM

@Composable
fun FilmDetailScreen(filmDetailViewModel: FilmDetailViewModel = viewModel(factory = FilmDetailViewModel.FACTORY)) {
    val film: Film by filmDetailViewModel.film.collectAsState(initial = EMPTY_FILM)
    Text(text = "Its details screen $film")
}