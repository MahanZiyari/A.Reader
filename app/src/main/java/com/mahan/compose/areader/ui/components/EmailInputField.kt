package com.mahan.compose.areader.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EmailInputField(
    modifier: Modifier = Modifier,
    emailState: String,
    onEmailChange: (String) -> Unit,
    onKeyboardActions: KeyboardActions
) {
    InputField(
        modifier = modifier,
        value = emailState,
        onValueChange = onEmailChange,
        label = "Email",
        leadingIcon = Icons.Default.Email,
        isSingleLine = true,
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next,
        onAction = onKeyboardActions,
    )
}