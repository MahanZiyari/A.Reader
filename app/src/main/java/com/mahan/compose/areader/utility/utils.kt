package com.mahan.compose.areader.utility

import android.content.Context
import android.widget.Toast

fun toastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun getCategory(categoryList: List<String>) {
    var stringForm = categoryList.toString()
}