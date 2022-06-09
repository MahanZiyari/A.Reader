package com.mahan.compose.areader.model

data class BookList(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)