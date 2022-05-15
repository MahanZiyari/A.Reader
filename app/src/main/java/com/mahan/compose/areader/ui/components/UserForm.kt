package com.mahan.compose.areader.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mahan.compose.areader.R

@ExperimentalComposeUiApi
@Composable
fun UserForm(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isCreateAccount: Boolean,
    onFinished: (String, String) -> Unit,
    onSwitchForm: () -> Unit
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val isInputValid = remember(key1 = email.value, key2 = password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isCreateAccount) {
            Text(text = stringResource(id = R.string.signup_guide))
        }

        EmailInputField(
            emailState = email.value,
            onEmailChange = {
                email.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            onKeyboardActions = KeyboardActions {
                passwordFocusRequest.requestFocus()
            }
        )

        PasswordInputField(
            modifier = Modifier
                .focusRequester(passwordFocusRequest)
                .padding(4.dp)
                .fillMaxWidth(),
            passwordStat = password.value,
            onPasswordChange = {
                password.value = it
            },
            onKeyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    if (!isInputValid) return@KeyboardActions
                    onFinished(email.value.trim(), password.value.trim())
                }
            )
        )
        
        SubmitButton(
            text = if (isCreateAccount) "Create Account" else "Login",
            isLoading = isLoading,
            isInputsValid = isInputValid
        ) {
            onFinished(email.value.trim(), password.value.trim())
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = if (isCreateAccount) "Already have an account? " else "New User? ",
                color = MaterialTheme.colors.onBackground
            )

            Text(
                text = if (isCreateAccount) "Login" else "Sign up",
                color = MaterialTheme.colors.secondaryVariant,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onSwitchForm()
                }
            )
        }
    }// End of Column Scope
}