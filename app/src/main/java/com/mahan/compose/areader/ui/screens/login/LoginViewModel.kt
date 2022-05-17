package com.mahan.compose.areader.ui.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mahan.compose.areader.model.MUser
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    //val loadingState = MutableStateFlow(LoadingState.IDLE)

    private val auth: FirebaseAuth = Firebase.auth


    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading


    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        navigateToHomeScreen: () -> Unit
    ) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    navigateToHomeScreen()
                }.addOnFailureListener {
                    Log.d("fuck:LoginViewModel", "Sign in Failed: ${it.message}")
                }
        } catch (e: Exception) {
            Log.d("fuck", "Error: ${e.message}")
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        navigateToHomeScreen: () -> Unit
    ) = viewModelScope.launch {
        if (_loading.value == false) {
            _loading.value = true
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val displayName = it?.user?.email?.substringBefore('@').toString()
                        addUserOnFirestore(displayName)
                        navigateToHomeScreen()
                    }.addOnFailureListener {
                        Log.d("fuck:LoginViewModel", "Sign up Failed: ${it.message}")
                    }
            } catch (e: Exception) {
                Log.d("fuck", "Error: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    private fun addUserOnFirestore(displayName: String) {
        val userId = auth.currentUser?.uid

        val user = MUser(
            id = null,
            userId = userId.toString(),
            displayName = displayName,
            quote = "",
            avatarUrl = "",
            profession = "Android Developer"
        ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }
}