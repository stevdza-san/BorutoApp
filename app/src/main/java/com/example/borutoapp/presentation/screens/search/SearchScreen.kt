package com.example.borutoapp.presentation.screens.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.borutoapp.presentation.common.ListContent

@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    val searchQuery by searchViewModel.searchQuery
    val heroes = searchViewModel.searchedHeroes.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(it)
                },
                onClosedClicked = {
                    navController.popBackStack()
                },
                onSearchClicked = {
                    searchViewModel.searchHeroes(query = it)
                }
            )
        },
        content = { paddingValue->
            val modifier = Modifier.padding(top = paddingValue.calculateTopPadding())
            ListContent(heroes = heroes, navController = navController)
        }
    )
}