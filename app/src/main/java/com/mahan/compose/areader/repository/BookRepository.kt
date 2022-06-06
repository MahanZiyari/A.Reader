package com.mahan.compose.areader.repository

import com.mahan.compose.areader.data.Resource
import com.mahan.compose.areader.model.Item
import com.mahan.compose.areader.network.GoogleBookAPI
import com.mahan.compose.areader.utility.Constants
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: GoogleBookAPI) {

    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {

        return try {
            Resource.Loading(data = true)

            val itemList = api.getAllBooks(searchQuery, Constants.API_KEY).items
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)

        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }

    }

    suspend fun getBookInfo(bookId: String): Resource<Item> {
        val response = try {
            Resource.Loading(data = true)
            api.getBookInfo(bookId)

        } catch (exception: Exception) {
            return Resource.Error(message = "An error occurred ${exception.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}