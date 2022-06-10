package com.mahan.compose.areader.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.mahan.compose.areader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(private val repository: BookRepository) :
    ViewModel() {

        suspend fun getBookInfo(bookId: String) = repository.getBookInfo(bookId)
}