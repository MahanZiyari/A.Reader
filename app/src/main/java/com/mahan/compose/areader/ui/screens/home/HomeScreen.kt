package com.mahan.compose.areader.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mahan.compose.areader.model.MBook
import com.mahan.compose.areader.ui.components.DrawerContent
import com.mahan.compose.areader.ui.components.FAB
import com.mahan.compose.areader.ui.components.HomeScreenTopAppBar
import com.mahan.compose.areader.ui.components.ListCard
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController
) {

    val scaffoldState = rememberScaffoldState()
    val drawerCoroutine = rememberCoroutineScope()
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
                //TODO : Implement FAB functionality -> navigate to search screen
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
            Text(
                text = "Active user: ${
                    FirebaseAuth.getInstance().currentUser?.email?.substringBefore("@")
                }"
            )

            ListCard(
                book = MBook(
                    id = "abc",
                    title = "Hello Compose",
                    authors = "someone",
                    notes = "dsadsfdsgd"
                )
            )
        }
    }
}