package kut.nikita.filmsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kut.nikita.filmsapp.ui.theme.FilmsAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "filmList") {
                        composable(
                            route = "filmList",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) {
                            FilmListScreen { id ->
                                navController.navigate("filmDetail/$id")
                            }
                        }
                        composable("filmDetail/{id}") {
                            FilmDetailScreen()
                        }
                    }
                }
            }
        }
    }
}
