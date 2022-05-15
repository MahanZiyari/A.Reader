package com.mahan.compose.areader.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppLogo(
    modifier: Modifier = Modifier
) {
    Text(
        text = "A. Reader",
        style = MaterialTheme.typography.h3,
        color = Color.Red.copy(alpha = 0.75f),
        modifier = modifier
    )
}


@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    trailingIcon: Int? = null,
    onTrailingIconClicked: () -> Unit = {},
    enabled: Boolean = true,
    isSingleLine: Boolean,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        label = { Text(text = label) },
        enabled = enabled,
        singleLine = isSingleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground
        ),
        shape = RoundedCornerShape(size = 5.dp),
        visualTransformation = visualTransformation,
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "leading Icon",
                tint = Color.LightGray
            )
        },
        trailingIcon = {
            trailingIcon?.let {
                IconButton(
                    onClick = onTrailingIconClicked
                ) {
                    Icon(
                        painter = painterResource(id = trailingIcon),
                        contentDescription = "trailing Icon",
                        tint = Color.DarkGray
                    )
                }
            }
        }
    )
}