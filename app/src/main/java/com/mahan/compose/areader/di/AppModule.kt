package com.mahan.compose.areader.di

import com.google.firebase.firestore.FirebaseFirestore
import com.mahan.compose.areader.network.GoogleBookAPI
import com.mahan.compose.areader.repository.FireRepository
import com.mahan.compose.areader.utility.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGoogleBookAPI(): GoogleBookAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleBookAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideFireBookRepository() =
        FireRepository(queryBook = FirebaseFirestore.getInstance().collection("books"))
}