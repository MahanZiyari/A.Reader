package com.mahan.compose.areader.network

import com.mahan.compose.areader.model.Book
import com.mahan.compose.areader.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface GoogleBookAPI {

    @GET(value = "volumes")
    suspend fun getAllBooks(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String
    ): Book

/*

    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Book
*/

    @GET(value = "volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Item
}