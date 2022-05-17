package com.mahan.compose.areader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mahan.compose.areader.ui.screens.SplashScreen
import com.mahan.compose.areader.ui.screens.detail.BookDetailsScreen
import com.mahan.compose.areader.ui.screens.home.HomeScreen
import com.mahan.compose.areader.ui.screens.login.LoginScreen
import com.mahan.compose.areader.ui.screens.search.BookSearchScreen
import com.mahan.compose.areader.ui.screens.stats.StatsScreen
import com.mahan.compose.areader.ui.screens.update.BookUpdateScreen

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
            BookSearchScreen(navController = navController)
        }

        //UpdateScreen
        composable(route = Destination.UpdateScreen.name) {
            BookUpdateScreen(navController = navController)
        }

        //DetailScreen
        composable(route = Destination.DetailScreen.name) {
            BookDetailsScreen(navController = navController)
        }

        //StatsScreen
        composable(route = Destination.StatsScreen.name) {
            StatsScreen(navController = navController)
        }

        //StatsScreen
        composable(route = Destination.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
    }
}