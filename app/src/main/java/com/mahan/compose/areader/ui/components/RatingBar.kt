package com.mahan.compose.areader.ui.components

import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mahan.compose.areader.R

@Composable
fun StaticRatingBar(
    modifier: Modifier,
    value: Int,
    starSize: Dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        (1..5).forEachIndexed { index, i ->
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "",
                tint = if (i <= value) Color.Yellow else Color.LightGray,
                modifier = Modifier.size(starSize)
            )
        }
    }
}

@Preview
@Composable
private fun MyRatingBar() {
    StaticRatingBar(modifier = Modifier, value = 3, starSize = 15.dp)
}


@ExperimentalComposeUiApi
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    onPressRating: (Int) -> Unit,
) {
    var ratingState by remember {
        mutableStateOf(rating)
    }

    var selected by remember {
        mutableStateOf(false)
    }
    val size by animateDpAsState(
        targetValue = if (selected) 42.dp else 34.dp,
        spring(Spring.DampingRatioMediumBouncy)
    )

    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_star_24),
                contentDescription = "star",
                modifier = modifier
                    .width(size)
                    .height(size)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                onPressRating(i)
                                ratingState = i
                            }
                            MotionEvent.ACTION_UP -> {
                                selected = false
                            }
                        }
                        true
                    },
                tint = if (i <= ratingState) Color(0xFFFFD700) else Color(0xFFA2ADB1)
            )
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
private fun Preview() {
    RatingBar(
        rating = 4,
        onPressRating = {},
        modifier = Modifier.scale(0.75f)
    )
}