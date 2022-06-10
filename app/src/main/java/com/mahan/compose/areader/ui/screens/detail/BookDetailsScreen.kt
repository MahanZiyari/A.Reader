package com.mahan.compose.areader.ui.screens.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.mahan.compose.areader.data.Resource
import com.mahan.compose.areader.model.Item
import com.mahan.compose.areader.ui.components.SimpleAppBar

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
            val thumbnailUrl =
                "https:".plus(book.volumeInfo.imageLinks.thumbnail.substringAfter(':'))
            Image(
                painter = rememberImagePainter(data = thumbnailUrl),
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
                // TODO: Implement Star RatingBar

                // Categories
                // TODO Implement Chips for Categories
            }
        }// End of Info Section

        // Add to collection Button
        Button(
            onClick = {
                //TODO Implement Save to Firestore
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
    }
}