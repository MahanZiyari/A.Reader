@file:OptIn(ExperimentalComposeUiApi::class)

package com.mahan.compose.areader.ui.screens.detail

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mahan.compose.areader.data.Resource
import com.mahan.compose.areader.model.Item
import com.mahan.compose.areader.model.MBook
import com.mahan.compose.areader.ui.components.SimpleAppBar
import com.mahan.compose.areader.ui.components.StaticRatingBar

@ExperimentalMaterialApi
@Composable
fun BookDetailsScreen(
    navController: NavHostController,
    viewModel: BookDetailsViewModel = hiltViewModel(),
    selectedBookId: String,
) {
    val bookInfo = produceState<Resource<Item>>(
        initialValue = Resource.Loading(),
    ) {
        value = viewModel.getBookInfo(selectedBookId)
    }.value

    var isDataReady = remember(key1 = bookInfo) {
        bookInfo.data != null
    }



    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SimpleAppBar(title = "About Book") {
                navController.popBackStack()
            }
        }
    ) {
        if (isDataReady) {
            ScreenContent(
                navController = navController,
                viewModel = viewModel,
                book = bookInfo.data!!
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun ScreenContent(
    navController: NavHostController,
    viewModel: BookDetailsViewModel,
    book: Item,
) {
    Log.d("DetailsScreen", "ScreenContent: book = ${book.volumeInfo.title}")
    //Text(text = book.volumeInfo.title, fontSize = 20.sp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .padding(12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        //Book Info Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.height(300.dp)
                .padding(6.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            val thumbnailUrl: String? =
                "https:".plus(book.volumeInfo.imageLinks?.thumbnail?.substringAfter(':'))
            Image(
                painter = rememberImagePainter(data = thumbnailUrl ?: ""),
                contentDescription = "Book Image",
                modifier = Modifier
                    .width(120.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(width = 1.dp, color = Color.DarkGray, RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillBounds
            )
            // Spacer for book image and it's info
            Spacer(modifier = Modifier.width(10.dp))

            // Info Setcion
            Column(
                modifier = Modifier.height(150.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                // Title
                Text(
                    text = book.volumeInfo.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(top = 10.dp)
                )

                // Authors
                Text(
                    text = "Authors: ${book.volumeInfo.authors}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    maxLines = 2,
                    modifier = Modifier.padding(top = 6.dp)
                )

                // Publish Date
                Text(
                    text = "Published At: ${book.volumeInfo.publishedDate}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(top = 6.dp)
                )


                // Rating Stars
                if (book.volumeInfo.averageRating > 0) {
                    StaticRatingBar(
                        modifier = Modifier.padding(top = 14.dp),
                        value = book.volumeInfo.averageRating.toInt(),
                        starSize = 20.dp
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        text = "Not Rated",
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Categories
                /*book.volumeInfo.categories?.let {
                    LazyRow(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        items(it) { string ->
                            Chip(
                                onClick = { *//*TODO*//* },
                                colors = ChipDefaults.chipColors(
                                    backgroundColor = Color(0xff4976dd)
                                ),
                                border = BorderStroke(
                                    width = 0.75.dp,
                                    color = Color.DarkGray
                                )
                            ) {
                                Text(text = string)
                            }
                        }
                    }
                }*/
            }
        }// End of Info Section

        // Add to collection Button
        //TODO : if book is already in user bookshelf then show disabled outline button
        Button(
            onClick = {
                val mBook = MBook(
                    title = book.volumeInfo.title,
                    authors = book.volumeInfo.authors.toString(),
                    description = book.volumeInfo.description,
                    categories = book.volumeInfo.categories.toString(),
                    notes = "",
                    photoUrl = book.volumeInfo.imageLinks?.thumbnail,
                    publishedDate = book.volumeInfo.publishedDate,
                    pageCount = book.volumeInfo.pageCount.toString(),
                    rating = book.volumeInfo.averageRating.toDouble(),
                    googleBookId = book.id,
                    userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
                )

                saveToFirebase(mBook, navController = navController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(6.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xff1d2bdc)
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Add to Collection",
                color = MaterialTheme.colors.onPrimary,
                fontSize = 18.sp
            )
        }


        // Book Description
        var textExpanded by remember {
            mutableStateOf(false)
        }

        val cleanDescription =
            HtmlCompat.fromHtml(book.volumeInfo.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 8.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            LazyColumn() {
                item {
                    Text(
                        text = cleanDescription + "\n",
                        textAlign = TextAlign.Start,
                        maxLines = if (textExpanded) 30 else 3,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Text(
                        text = if (textExpanded) "Read Less" else "Read More",
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.clickable {
                            textExpanded = !textExpanded
                        }
                    )
                }
            }
        }
    }
}

fun saveToFirebase(book: MBook, navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if (book.toString().isNotEmpty()) {
        dbCollection.add(book)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) navController.popBackStack()
                    }
                    .addOnFailureListener {
                        Log.w("Error", "SaveToFirebase:  Error updating doc", it)
                    }

            }
    }
}
