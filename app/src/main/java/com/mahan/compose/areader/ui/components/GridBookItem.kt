package com.mahan.compose.areader.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mahan.compose.areader.model.MBook

@Composable
fun GridBookItem(
    modifier: Modifier = Modifier,
    book: MBook,
    onClicked: (String) -> Unit,
) {

    val context = LocalContext.current
    val resources = context.resources

    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp
    Card(
        modifier = modifier
            .height(230.dp)
            .width(202.dp)
            .clip(RoundedCornerShape(30.dp))
            .clickable {
                onClicked(book.googleBookId!!)
            },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 6.dp,
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(width = 1.dp, color = Color.DarkGray)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .width(screenWidth.dp - (spacing * 2))
                .height(140.dp)
                .fillMaxHeight()
        ) {
            Row(horizontalArrangement = Arrangement.Center) {

                val imageUrl =
                    "https:".plus(book.photoUrl?.substringAfter(':'))
                // Image of book
                Image(
                    painter = rememberImagePainter(data = imageUrl),
                    contentDescription = "Book Cover",
                    modifier = Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .width(100.dp)
                        .height(140.dp)
                        .padding(10.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.weight(1f))


                // favoriteIcon and rating
                Column(
                    modifier = Modifier.padding(top = 15.dp, end = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "favorite book",
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    BookRating(score = 4.5)

                }
            }// End of Image and book rate

            Text(
                text = book.title!!,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )

            Text(
                text = book.authors!!,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {

                RoundReadingButton(label = "Reading", radius = 30.dp) {
                    // OnClick
                }
            }


        }// End of Whole card content which is column
    }
}


@Preview
@Composable
private fun Preview() {
    GridBookItem(
        book = MBook(
            id = "abc",
            title = "Hello Compose",
            authors = "someone",
            notes = "dsadsfdsgd"
        )
    ) {}
}