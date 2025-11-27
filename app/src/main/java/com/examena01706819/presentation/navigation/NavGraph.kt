package com.examena01706819.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.examena01706819.presentation.screens.home.HomeView
import com.examena01706819.presentation.screens.puzzle.PuzzleView

sealed class Screen(
    val route: String,
) {
    object Home : Screen("home")

    object Puzzle : Screen("puzzle/{dificulty}") {
        fun createRoute(dificulty: String) = "puzzle/$dificulty"
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun SudokuNavGraph(
    // Lab2.4 : Creamos el Modifier por si en algun momento se manda alguno.
    modifier: Modifier = Modifier,
    // Lab2.4 : El NavHostController es el navegador, que maneja la pila de navegación y permite moverse
    // por varias pantallas.
    // Lab2.4 :rememberNavController es una funcion que crea una instancia del NavHostController.
    navController: NavHostController = rememberNavController(),
) {
    // Lab2.4 : Contenedor de las pantallas
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        // Lab2.4 : Pantalla de HOME
        composable(route = Screen.Home.route) {
            HomeView(
                onPuzzleChosen = { dif ->
                    navController.navigate(
                        Screen.Puzzle.createRoute(dif),
                    )
                },
            )
        }

        composable(
            route = Screen.Puzzle.route,
            arguments = listOf(navArgument("dificulty") { type = NavType.StringType }),
        ) { backStackEntry ->
            val dif = backStackEntry.arguments?.getString("dificulty") ?: "medium"
            PuzzleView(
                dificulty = dif,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
