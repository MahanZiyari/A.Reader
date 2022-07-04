package com.mahan.compose.areader.ui.screens.update

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahan.compose.areader.model.MBook
import com.mahan.compose.areader.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateScreenViewModel @Inject constructor(private val repository: FireRepository) :
    ViewModel() {

    var selectedBook: MutableState<MBook?> = mutableStateOf(null)

    var openDialog = mutableStateOf(false)

    init {
    }

    fun findSelectedBookFromFirestore(bookGoogleId: String) {
        viewModelScope.launch {
            selectedBook.value = repository.getAllBooksFromDatabase().data?.first {
                it.googleBookId == bookGoogleId
            }!!
        }
    }


    var noteTextState =
        mutableStateOf(if (selectedBook.value?.notes.toString().isNotEmpty()) selectedBook.value?.notes
        else "No thought available :)")

    fun updateNoteTextState(newValue: String) {
        noteTextState.value = newValue
    }
}