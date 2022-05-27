package com.mahan.compose.areader.ui.screens.stats

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun StatsScreen(
    navController: NavHostController
) {
    Text(text = "Stats Screen", fontSize = 30.sp)
}