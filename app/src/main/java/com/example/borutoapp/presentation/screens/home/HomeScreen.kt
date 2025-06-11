package com.example.borutoapp.presentation.screens.home

import androidx.activity.compose.LocalActivity
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.borutoapp.navigation.Screen
import com.example.borutoapp.presentation.common.ListContent
import com.example.borutoapp.ui.theme.statusBarColor
import com.example.borutoapp.ui.theme.welcomeScreenBackgroundColor

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val activity = LocalActivity.current
    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()
    val systemBarColor = statusBarColor.toArgb()

    SideEffect { activity?.window?.statusBarColor = systemBarColor }

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                }
            )
        },
        containerColor = welcomeScreenBackgroundColor,
        content = { padding ->
            ListContent(
                padding = padding,
                heroes = allHeroes,
                navController = navController
            )
        }
    )
}