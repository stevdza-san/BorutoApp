package com.example.borutoapp.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.borutoapp.presentation.screens.details.DetailsScreen
import com.example.borutoapp.presentation.screens.details.DetailsViewModel
import com.example.borutoapp.presentation.screens.home.HomeScreen
import com.example.borutoapp.presentation.screens.home.HomeViewModel
import com.example.borutoapp.presentation.screens.search.SearchScreen
import com.example.borutoapp.presentation.screens.search.SearchViewModel
import com.example.borutoapp.presentation.screens.splash.SplashScreen
import com.example.borutoapp.presentation.screens.splash.SplashViewModel
import com.example.borutoapp.presentation.screens.welcome.WelcomeScreen
import com.example.borutoapp.presentation.screens.welcome.WelcomeViewModel
import com.example.borutoapp.util.Constants.DETAILS_ARGUMENT_KEY
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SetUpNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            val splashViewModel = hiltViewModel< SplashViewModel>()
            SplashScreen(navController = navController, splashViewModel = splashViewModel)
        }
        composable(route = Screen.Welcome.route) {
            val welcomeViewModel = hiltViewModel<WelcomeViewModel>()
            WelcomeScreen(navController = navController, welcomeViewModel = welcomeViewModel)
        }
        composable(route = Screen.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController, homeViewModel = homeViewModel)
        }
        composable(route = Screen.Search.route) {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(
                navController,
                searchViewModel
            )
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY){
                type = NavType.IntType
            })
        ) {
            val detailsViewModel: DetailsViewModel = hiltViewModel<DetailsViewModel>()
            DetailsScreen(
                navController = navController,
                detailsViewModel = detailsViewModel
            )
        }
    }
}