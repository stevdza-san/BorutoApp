package com.example.borutoapp.presentation.screens.details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsScreen(
    navController: NavHostController,
    detailsViewModel: DetailsViewModel
    ) {

    val selectedHero by detailsViewModel.selectedHero.collectAsState()
    DetailsContent(
        navController = navController,
        selectedHero = selectedHero
    )
}