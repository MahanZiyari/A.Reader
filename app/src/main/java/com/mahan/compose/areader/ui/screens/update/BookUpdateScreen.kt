package com.mahan.compose.areader.ui.screens.update

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mahan.compose.areader.R
import com.mahan.compose.areader.model.MBook
import com.mahan.compose.areader.ui.components.HorizontalBookDetail
import com.mahan.compose.areader.ui.components.InputField
import com.mahan.compose.areader.ui.components.UpdateScreenAppBar
import com.mahan.compose.areader.ui.navigation.Destination
import com.mahan.compose.areader.ui.screens.home.HomeScreenViewModel
import com.mahan.compose.areader.utility.formatDate
import com.mahan.compose.areader.utility.toastMessage

@ExperimentalComposeUiApi
@Composable
fun BookUpdateScreen(
    navController: NavHostController,
    bookItemId: String,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    updateScreenViewModel: UpdateScreenViewModel = hiltViewModel(),
) {


    val allBookInCollection by viewModel.data
    val currentUser = FirebaseAuth.getInstance().currentUser


    val book = remember(key1 = allBookInCollection) {
        if (!allBookInCollection.data.isNullOrEmpty()) {
            viewModel.data.value.data?.toList()?.filter { mBook ->
                mBook.userId == currentUser?.uid.toString()
            }?.first {
                it.googleBookId == bookItemId
            }
        } else {
            null
        }
    }

    updateScreenViewModel.selectedBook.value = book

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            UpdateScreenAppBar(title = "Update Book",
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSaveClicked = { /*TODO*/ },
                onDeleteClicked = {}
            )
        }
    ) {
        updateScreenViewModel.selectedBook.value?.let {
            Content(navController = navController, viewModel = viewModel, currentBook = it)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun Content(
    navController: NavHostController,
    viewModel: HomeScreenViewModel,
    currentBook: MBook,
) {

    val context = LocalContext.current
    var bookNoteTextState by remember {
        mutableStateOf(if (currentBook.notes.toString()
                .isNotEmpty()
        ) currentBook.notes else "No thought available :)")
    }

    val isNoteValid = remember(key1 = bookNoteTextState) {
        bookNoteTextState?.trim()?.isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    var isStartedReading by remember {
        mutableStateOf(false)
    }
    var isFinishedReading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .padding(12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        //Book Info
        HorizontalBookDetail(book = null, mBook = currentBook)

        //TextField for user note about this book
        InputField(
            value = bookNoteTextState!!,
            onValueChange = {
                bookNoteTextState = it
            },
            modifier = Modifier
                .fillMaxWidth()
                //.height(60.dp)
                .padding(6.dp),
            label = "Enter Your Thoughts",
            leadingIcon = null,
            isSingleLine = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            onAction = KeyboardActions {
                if (isNoteValid == false) return@KeyboardActions
                keyboardController?.hide()
                //TODO: Getting focus from this composable
            }
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { isStartedReading = true },
                enabled = currentBook.startedReading == null,
                modifier = Modifier
                    .height(50.dp)
                    .padding(4.dp)
                    .weight(50f),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary
                )
            ) {
                if (currentBook.startedReading == null) {
                    Text(text = if (!isStartedReading) "Start Reading" else "Started Reading!")
                } else {
                    Text(text = "Started On: ${formatDate(currentBook.startedReading!!)}")
                }
            }

            OutlinedButton(
                onClick = { isFinishedReading = true },
                enabled = currentBook.finishedReading == null,
                modifier = Modifier
                    .height(50.dp)
                    .padding(4.dp)
                    .weight(50f),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary
                )
            ) {
                if (currentBook.finishedReading == null) {
                    Text(text = if (!isFinishedReading) "Mark as Read" else "Finished Reading!")
                } else {
                    Text(text = "Finished On: ${formatDate(currentBook.finishedReading!!)}")
                }
            }
        }

        // -------------
        Spacer(modifier = Modifier.padding(bottom = 15.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {

            val changedNotes = currentBook.notes != bookNoteTextState
            val isFinishedTimeStamp =
                if (isFinishedReading) Timestamp.now() else currentBook.finishedReading
            val isStartedTimeStamp =
                if (isStartedReading) Timestamp.now() else currentBook.startedReading

            val bookUpdate =
                changedNotes || isStartedReading || isFinishedReading

            val bookToUpdate = hashMapOf(
                "finished_reading_at" to isFinishedTimeStamp,
                "started_reading_at" to isStartedTimeStamp,
                "notes" to bookNoteTextState).toMap()

            OutlinedButton(
                onClick = {
                    if (bookUpdate) {
                        FirebaseFirestore.getInstance()
                            .collection("books")
                            .document(currentBook.id!!)
                            .update(bookToUpdate)
                            .addOnCompleteListener {
                                toastMessage(context, "Book Updated Successfully!")
                                navController.navigate(route = Destination.HomeScreen.name)

                                // Log.d("Update", "ShowSimpleForm: ${task.result.toString()}")

                            }.addOnFailureListener {
                                Log.w("Error", "Error updating document", it)
                            }
                    }
                },
                enabled = currentBook.finishedReading == null,
                modifier = Modifier
                    .height(50.dp)
                    .padding(4.dp)
                    .weight(50f),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary
                )
            ) {
                Text(text = "Update")
            }

            /*RoundReadingButton(
                label = "Update",
                radius = 20.dp,
                onClicked = {

                }
            )*/
            Spacer(modifier = Modifier.width(100.dp))
            val openDialog = remember {
                mutableStateOf(false)
            }
            if (openDialog.value) {
                ShowAlertDialog(
                    message = stringResource(id = R.string.sure) + "\n" +
                            stringResource(id = R.string.action),
                    openDialog
                ) {
                    FirebaseFirestore.getInstance()
                        .collection("books")
                        .document(currentBook.id!!)
                        .delete()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                openDialog.value = false
                                /*
                                 Don't popBackStack() if we want the immediate recomposition
                                 of the MainScreen UI, instead navigate to the mainScreen!
                                */

                                navController.popBackStack()
                            }
                        }

                }

            }

            /*RoundReadingButton(
                label = "Delete",
                radius = 10.dp
            ) {
                openDialog.value = true
            }*/
        }
    }
}

@Composable
fun ShowAlertDialog(
    message: String,
    openDialog: MutableState<Boolean>,
    onYesPressed: () -> Unit,
) {

    if (openDialog.value) {
        AlertDialog(onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Delete Book") },
            text = { Text(text = message) },
            buttons = {
                Row(modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center) {
                    TextButton(onClick = { onYesPressed.invoke() }) {
                        Text(text = "Yes")

                    }
                    TextButton(onClick = { openDialog.value = false }) {
                        Text(text = "No")

                    }

                }
            })
    }
}
