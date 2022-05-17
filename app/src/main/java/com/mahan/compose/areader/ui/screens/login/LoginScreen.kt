package com.mahan.compose.areader.ui.screens.login

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mahan.compose.areader.ui.components.AppLogo
import com.mahan.compose.areader.ui.components.UserForm
import com.mahan.compose.areader.ui.navigation.Destination

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val isCreateAccount = rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLogo(
            modifier = Modifier
                .padding(top = 30.dp, bottom = 16.dp)
                .scale(1.3f)
        )

        UserForm(
            modifier = Modifier
                .padding(vertical = 50.dp, horizontal = 16.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                ),
            isLoading = viewModel.loading.value!!,
            isCreateAccount = isCreateAccount.value,
            onFinished = { email, password ->
                if (isCreateAccount.value) {
                    // Sign up
                    viewModel.createUserWithEmailAndPassword(email, password) {
                        navController.navigate(route = Destination.HomeScreen.name)
                    }
                } else {
                    // login
                    viewModel.signInWithEmailAndPassword(email, password) {
                        navController.navigate(route = Destination.HomeScreen.name)
                    }
                }
            },
            onSwitchForm = {
                isCreateAccount.value = !isCreateAccount.value
            }
        )
    }
}


@ExperimentalComposeUiApi
@Preview (showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}