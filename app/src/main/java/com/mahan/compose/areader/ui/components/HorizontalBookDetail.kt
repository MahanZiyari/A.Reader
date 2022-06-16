package com.mahan.compose.areader.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mahan.compose.areader.model.Item
import com.mahan.compose.areader.model.MBook

@Composable
fun HorizontalBookDetail(
    book: Item?,
    mBook: MBook?
) {
    if (book != null) {
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
            }
        }
    }
    if (mBook != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.height(300.dp)
                .padding(6.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            val thumbnailUrl: String? =
                "https:".plus(mBook.photoUrl?.substringAfter(':'))
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
                    text = mBook.title!!,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(top = 10.dp)
                )

                // Authors
                Text(
                    text = "Authors: ${mBook.authors}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    maxLines = 2,
                    modifier = Modifier.padding(top = 6.dp)
                )

                // Publish Date
                Text(
                    text = "Published At: ${mBook.publishedDate}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(top = 6.dp)
                )


                // Rating Stars
                if (mBook.rating!! > 0) {
                    StaticRatingBar(
                        modifier = Modifier.padding(top = 14.dp),
                        value = mBook.rating!!.toInt(),
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
            }
        }
    }

}