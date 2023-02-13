package kut.nikita.filmsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kut.nikita.filmsapp.ui.theme.FilmsAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmsAppTheme {
                FilmListFragment()
            }
        }
    }
}
