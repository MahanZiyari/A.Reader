package com.mahan.compose.areader.utility

import android.content.Context
import android.icu.text.DateFormat
import android.widget.Toast
import com.google.firebase.Timestamp

fun toastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun formatDate(timestamp: Timestamp): String {
    return DateFormat.getDateInstance().format(timestamp.toDate()).toString().split(",")[0] // March 12
}