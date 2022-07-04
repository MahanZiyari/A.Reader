package com.mahan.compose.areader.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UpdateScreenAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigateBack: () -> Unit,
    onSaveClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back to Home Screen",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        actions = {
            IconButton(
                onClick = onDeleteClicked
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Action",
                    tint = MaterialTheme.colors.onPrimary
                )
            }

            IconButton(
                onClick = onSaveClicked
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Update Action",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    UpdateScreenAppBar(
        title = "Update Book",
        onNavigateBack = {},
        onSaveClicked = {},
        onDeleteClicked = {}
    )
}