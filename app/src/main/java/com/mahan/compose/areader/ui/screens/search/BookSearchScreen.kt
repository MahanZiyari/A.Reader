package com.mahan.compose.areader.ui.screens.search

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mahan.compose.areader.ui.components.SearchScreenTopAppBar

@Composable
fun BookSearchScreen(
    navController: NavHostController,
    viewModel: BookSearchViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchScreenTopAppBar(
                title = "Search Books",
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Log.d("Searchx", "${viewModel.list.size}")
    }
}