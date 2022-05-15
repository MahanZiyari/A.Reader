package com.mahan.compose.areader.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.mahan.compose.areader.R

@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    passwordStat: String,
    onKeyboardActions: KeyboardActions,
    onPasswordChange: (String) -> Unit
) {
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }

    val visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
    val visibilityIcon = if (passwordVisibility) R.drawable.ic_visibility else R.drawable.ic_visibility_off

    InputField(
        modifier = modifier,
        value = passwordStat,
        onValueChange = onPasswordChange,
        label = "Password",
        leadingIcon = Icons.Default.Lock,
        isSingleLine = true,
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done,
        visualTransformation = visualTransformation,
        trailingIcon = visibilityIcon,
        onTrailingIconClicked = {
            passwordVisibility = !passwordVisibility
        },
        onAction = onKeyboardActions
    )
}