package com.mahan.compose.areader.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    imageUrl: String,
    userEmail: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        ProfileSection(
            userEmail = userEmail,
            modifier = Modifier.clickable {
                // Navigate to StatsScreen
            }
        )
        Divider(
            color = MaterialTheme.colors.onSurface.copy(0.5f),
            //startIndent = 10.dp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Preview (showBackground = true)
@Composable
private fun Preview() {
    DrawerContent(imageUrl = "", userEmail = "mahan@gmail.com")
}