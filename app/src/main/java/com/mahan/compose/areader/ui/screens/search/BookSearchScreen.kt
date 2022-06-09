package com.mahan.compose.areader.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mahan.compose.areader.model.Item
import com.mahan.compose.areader.ui.components.BookItem
import com.mahan.compose.areader.ui.components.InputField
import com.mahan.compose.areader.ui.components.SearchScreenTopAppBar
import com.mahan.compose.areader.utility.toastMessage

@ExperimentalComposeUiApi
@Composable
fun BookSearchScreen(
    navController: NavHostController,
    viewModel: BookSearchViewModel = hiltViewModel(),
) {
    val listOfBooks = viewModel.list
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
        ScreenContent(navController = navController, viewModel = viewModel, listOfBooks)
    }
}

@ExperimentalComposeUiApi
@Composable
private fun ScreenContent(
    navController: NavHostController,
    viewModel: BookSearchViewModel,
    listOfBooks: List<Item>,
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputField(
            value = viewModel.searchQuery.value,
            onValueChange = {
                viewModel.searchQuery.value = it
            },
            label = "Search Book",
            leadingIcon = Icons.Default.Search,
            isSingleLine = true,
            enabled = !viewModel.isLoading,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            onAction = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    if (viewModel.searchQuery.value.isNotEmpty()) {
                        toastMessage(context, "searching")
                        viewModel.isLoading = true
                        viewModel.searchBooks()
                    } else if (viewModel.searchQuery.value.isEmpty()) {
                        toastMessage(context, "Oops empty")
                        //TODO: Implement a good UX for empty search query
                    }
                }
            )
        )

        if (!viewModel.isLoading) {
            // Design for showing books
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(listOfBooks) { item ->
                    BookItem(book = item) { bookId ->
                        toastMessage(context, "Selected book: $bookId")
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }

}