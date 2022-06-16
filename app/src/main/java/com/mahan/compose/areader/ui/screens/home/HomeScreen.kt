@file:OptIn(ExperimentalFoundationApi::class)

package com.mahan.compose.areader.ui.screens.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mahan.compose.areader.model.MBook
import com.mahan.compose.areader.ui.components.DrawerContent
import com.mahan.compose.areader.ui.components.FAB
import com.mahan.compose.areader.ui.components.GridBookItem
import com.mahan.compose.areader.ui.components.HomeScreenTopAppBar
import com.mahan.compose.areader.ui.navigation.Destination
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = true) {
        viewModel.getAllBooksFromDatabase()
    }

    val scaffoldState = rememberScaffoldState()
    val drawerCoroutine = rememberCoroutineScope()

    //var listOfBooks: List<MBook> = emptyList()
    val currentUser = FirebaseAuth.getInstance().currentUser

    val allBookInCollection by viewModel.data

    val listOfBooks = remember(key1 = allBookInCollection) {
        if (!allBookInCollection.data.isNullOrEmpty()) {
            viewModel.data.value.data!!.toList().filter { mBook ->
                mBook.userId == currentUser?.uid.toString()
            }
        } else {
            emptyList()
        }
    }

    //Log.d("Books", "HomeContent: ${listOfBooks.last()}")


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            HomeScreenTopAppBar(
                title = "A.reader",
                navController = navController,
                navigationButtonOnClick = {
                    if (scaffoldState.drawerState.isOpen)
                        drawerCoroutine.launch { scaffoldState.drawerState.close() }
                    else if (scaffoldState.drawerState.isClosed)
                        drawerCoroutine.launch { scaffoldState.drawerState.open() }
                }
            )
        },
        floatingActionButton = {
            FAB(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                icon = Icons.Default.Add,
                tint = MaterialTheme.colors.onPrimary
            ) {
                navController.navigate(route = Destination.SearchScreen.name)
            }
        },
        drawerContent = {
            DrawerContent(
                imageUrl = "",
                userEmail = FirebaseAuth.getInstance().currentUser?.email.toString()
            )
        },
        drawerShape = MaterialTheme.shapes.small
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            //HomeScreen Content
            HomeScreenContent(navController = navController, listOfBooks = listOfBooks)
        }
    }
}

@Composable
private fun HomeScreenContent(navController: NavHostController, listOfBooks: List<MBook>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
    ) {
        items(listOfBooks) { mBook ->
            GridBookItem(
                book = mBook,
                modifier = Modifier.padding(8.dp),
                onClicked = {
                    Log.d("Home", "Are equal? ${it == mBook.googleBookId}")
                    navController.navigate(route = Destination.UpdateScreen.name + "/$it")
                }
            )
        }
    }
}