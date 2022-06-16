package com.mahan.compose.areader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mahan.compose.areader.ui.screens.SplashScreen
import com.mahan.compose.areader.ui.screens.detail.BookDetailsScreen
import com.mahan.compose.areader.ui.screens.home.HomeScreen
import com.mahan.compose.areader.ui.screens.home.HomeScreenViewModel
import com.mahan.compose.areader.ui.screens.login.LoginScreen
import com.mahan.compose.areader.ui.screens.search.BookSearchScreen
import com.mahan.compose.areader.ui.screens.search.BookSearchViewModel
import com.mahan.compose.areader.ui.screens.stats.StatsScreen
import com.mahan.compose.areader.ui.screens.update.BookUpdateScreen

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun Navigation() {
    val navController = rememberAnimatedNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.SplashScreen.name,
    ) {

        //SplashScreen
        composable(route = Destination.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        //LoginScreen
        composable(route = Destination.LoginScreen.name) {
            LoginScreen(navController = navController)
        }


        //SearchScreen
        composable(route = Destination.SearchScreen.name) {
            val searchViewModel = hiltViewModel<BookSearchViewModel>()
            BookSearchScreen(navController = navController, viewModel = searchViewModel)
        }

        //UpdateScreen
        val updateScreenBasicRoute = Destination.UpdateScreen.name
        composable(
            route = "$updateScreenBasicRoute/{bookItemId}",
            arguments = listOf(
                navArgument(
                    name = "bookItemId",
                ) { type = NavType.StringType }
            )
        ) { navBackStackEntry ->

            navBackStackEntry.arguments?.getString("bookItemId").let {
                BookUpdateScreen(navController = navController, bookItemId = it.toString())
            }
        }

        //DetailScreen
        val detailsScreenBasicRoute = Destination.DetailScreen.name
        composable(
            route = "$detailsScreenBasicRoute/{bookId}",
            arguments = listOf(
                navArgument(
                    name = "bookId",
                ) { type = NavType.StringType }
            )
        ) { BackStackEntry ->
            BackStackEntry.arguments?.getString("bookId").let { bookId ->
                BookDetailsScreen(
                    navController = navController,
                    selectedBookId = bookId.toString()
                )
            }
        }

        //StatsScreen
        composable(route = Destination.StatsScreen.name) {
            StatsScreen(navController = navController)
        }

        //HomeScreen
        composable(route = Destination.HomeScreen.name) {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(navController = navController, viewModel = viewModel)
        }
    }
}