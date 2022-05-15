package com.mahan.compose.areader.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean,
    isInputsValid: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 4.dp)
            .fillMaxWidth(),
        onClick = onClick,
        shape = CircleShape,
        enabled = !isLoading && isInputsValid
    ) {
        if (isLoading) CircularProgressIndicator(
            modifier = Modifier.size(25.dp),
            color = MaterialTheme.colors.primary
        ) else
            Text(
                text = text,
                modifier = Modifier.padding(4.dp),
                fontSize = 20.sp
            )
    }
}

@Preview
@Composable
private fun SubmitButtonPreview() {
    SubmitButton(text = "Create Account", isLoading = false, isInputsValid = true) {}
}