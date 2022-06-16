package com.mahan.compose.areader.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mahan.compose.areader.R
import com.mahan.compose.areader.ui.navigation.Destination

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
    leadingIcon: ImageVector?,
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
        shape = RoundedCornerShape(size = 10.dp),
        visualTransformation = visualTransformation,
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "leading Icon",
                    tint = Color.LightGray
                )
            }
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


@Composable
fun HomeScreenTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    showProfile: Boolean = true,
    navController: NavHostController,
    navigationButtonOnClick: () -> Unit
) {

    var menuExpended by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navigationButtonOnClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Drawer",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        actions = {

            IconButton(
                onClick = {
                    menuExpended = true
                }
            ) {
                DropdownMenu(
                    expanded = menuExpended,
                    onDismissRequest = { menuExpended = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            menuExpended = false
                            FirebaseAuth.getInstance().signOut().run {
                                navController.navigate(route = Destination.LoginScreen.name) {
                                    popUpTo(0)
                                }
                            }
                        }
                    ) {
                        Text(
                            text = "Logout",
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }


                Icon(
                    painter = painterResource(id = R.drawable.ic_kebab),
                    contentDescription = "Logout",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    )
}



@Composable
fun FAB(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    icon: ImageVector,
    tint: Color,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        backgroundColor = backgroundColor,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Add a book",
            tint = tint
        )
    }
}




@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    userEmail: String,
    imageUrl: String? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.avatar_placeholder),
            contentDescription = "User Profile Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(2.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            //modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Text(
                text = userEmail.substringBefore('@'),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                fontSize = 24.sp,
            )
            Text(
                text = userEmail,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileSectionPreview() {
    ProfileSection(
        userEmail = "mahan@gamil.com"
    )
}


@Composable
fun BookRating(score: Double) {
    Surface(
        modifier = Modifier
            .height(70.dp)
            .padding(4.dp),
        color = Color.White,
        elevation = 4.dp,
        shape = RoundedCornerShape(60.dp)
    ) {

        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                modifier = Modifier.padding(2.dp)
            )

            Text(text = score.toString(), fontSize = 12.sp)
        }
    }
}

@Composable
fun RoundReadingButton(
    label: String,
    radius: Dp,
    onClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(90.dp)
            .height(90.dp)
            .clickable { onClicked() },
        shape = RoundedCornerShape(topStart = radius, bottomEnd = radius),
        color = MaterialTheme.colors.primary
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 15.sp,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun RoundReadingButton() {
    RoundReadingButton(label = "Reading", radius = 30.dp) {
    }
}