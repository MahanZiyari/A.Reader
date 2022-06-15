package com.mahan.compose.areader.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahan.compose.areader.data.DataOrException
import com.mahan.compose.areader.model.MBook
import com.mahan.compose.areader.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: FireRepository) : ViewModel() {

    val data: MutableState<DataOrException<List<MBook>, Boolean, Exception>> =
        mutableStateOf(DataOrException(listOf(), true, Exception("")))

    init {
        getAllBooksFromDatabase()
    }

    private fun getAllBooksFromDatabase() {
        viewModelScope.launch {
            data.value.isLoading = true
            data.value = repository.getAllBooksFromDatabase()
            if (!data.value.data.isNullOrEmpty()) data.value.isLoading = false
        }
    }
}