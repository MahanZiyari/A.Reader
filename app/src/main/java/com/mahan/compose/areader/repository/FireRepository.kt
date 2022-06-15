package com.mahan.compose.areader.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.mahan.compose.areader.data.DataOrException
import com.mahan.compose.areader.model.MBook
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(private val queryBook: Query) {

    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Boolean, Exception> {
        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()

        try {
            dataOrException.isLoading = true
            dataOrException.data = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }
            if (!dataOrException.data.isNullOrEmpty()) dataOrException.isLoading = false
        }catch (exception: FirebaseFirestoreException) {
            dataOrException.e = exception
        }
        return dataOrException
    }
}