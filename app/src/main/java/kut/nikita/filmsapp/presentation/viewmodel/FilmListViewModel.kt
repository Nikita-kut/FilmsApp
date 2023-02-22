package kut.nikita.filmsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kut.nikita.filmsapp.domain.FilmListUseCase
import kut.nikita.filmsapp.domain.model.Film
import kut.nikita.filmsapp.presentation.FilmsApp

class FilmListViewModel(
    private val filmListUseCase: FilmListUseCase,
) : ViewModel() {

    val selectedChip get() = _selectedChip
    private val _selectedChip = MutableStateFlow<String?>(null)

    val searchText: Flow<String> get() = _searchText
    private val _searchText = MutableStateFlow("")

    val chips: Flow<List<String>> get() = _chips
    private val _chips = MutableSharedFlow<List<String>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    val films: Flow<List<Film>> get() = _films
    private val _films = MutableSharedFlow<List<Film>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    private var allFilms = emptyList<Film>()

    private val searchTextValue get() = _searchText.value

    private val selectedChipValue get() = _selectedChip.value

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        logError(exception)
    }

    private val loadJobs = listOf(
        viewModelScope.launch {
            _chips.emit(filmListUseCase.fetchChipsList())
        },
        viewModelScope.launch {
            filmListUseCase.fetchFilmList().also {
                _films.emit(it)
                allFilms = it
            }
        }
    )

    init {
        viewModelScope.launch(exceptionHandler) {
            loadJobs.joinAll()
        }
    }

    fun onChipClick(chip: String?) {
        _selectedChip.value = chip
        filterFilmsBySearchAndChip(searchTextValue, chip)
    }

    fun changeSearchText(text: String) {
        _searchText.value = text
        filterFilmsBySearchAndChip(text, selectedChipValue)
    }

    fun clearSearchText() {
        _searchText.value = ""
        filterFilmsBySearchAndChip("", selectedChipValue)
    }

    private fun filterFilmsBySearchAndChip(query: String, chip: String?) {
        viewModelScope.launch {
            flowOf(query)
                .map { it.lowercase().trim() }
                .debounce(DEBOUNCE_TIME)
                .combine(flowOf(chip)) { _query, _chip ->
                    when {
                        _chip == null && _query.isBlank() -> allFilms
                        _chip == null && _query.isNotBlank() -> filterByQuery(_query)
                        _chip != null && _query.isBlank() -> filterByChip(_chip)
                        else -> filterByQueryAndChip(_query, _chip)
                    }
                }
                .catch { logError(it) }
                .collect {
                    _films.emit(it)
                }
        }
    }

    private fun logError(e: Throwable) {
        Log.e(TAG, e.stackTraceToString())
    }

    private fun filterByQueryAndChip(query: String, chip: String?) = allFilms.filter {
        it.name.contains(query, true) && it.genre == chip
    }

    private fun filterByQuery(query: String) = allFilms.filter { it.name.contains(query, true) }

    private fun filterByChip(chip: String?) = allFilms.filter { it.genre == chip }

    companion object {
        private const val TAG = "FilmListVM"
        private const val DEBOUNCE_TIME = 300L

        val FACTORY = viewModelFactory {
            initializer {
                val useCase = (this[APPLICATION_KEY] as FilmsApp).filmListUseCase
                FilmListViewModel(useCase)
            }
        }
    }
}