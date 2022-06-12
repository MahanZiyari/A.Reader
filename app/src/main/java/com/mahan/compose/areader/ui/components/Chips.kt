@file:OptIn(ExperimentalMaterialApi::class)

package com.mahan.compose.areader.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
private fun Chips(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color,
    textColor: Color
) {

}

@Preview
@Composable
private fun ChipsPreview() {
    //Chips(title = "Programming", backgroundColor = Color(0xff4976dd), textColor = Color.Black)
    Chip(
        onClick = { /*TODO*/ },
        colors = ChipDefaults.chipColors(
            backgroundColor = Color(0xff4976dd)
        ),
        border = BorderStroke(
            width = 0.75.dp,
            color = Color.DarkGray
        )
    ) {
        Text(text = "Programming")
    }
}