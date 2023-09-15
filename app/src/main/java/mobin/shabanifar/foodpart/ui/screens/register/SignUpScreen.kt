package mobin.shabanifar.foodpart.ui.screens.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import mobin.shabanifar.foodpart.data.models.Result
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.data.models.sign_up.SignUpBody
import mobin.shabanifar.foodpart.ui.theme.blue
import mobin.shabanifar.foodpart.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(
    navigateToProfileLogin: () -> Unit, // Callback for navigating to profile login screen
    navigateToProfile: () -> Unit, // Callback for navigating to profile screen
    saveUserName: (String) -> Unit, // Callback for get the username
    isLogin: (Boolean) -> Unit, // Callback for indicating login status
    viewModel: SignUpViewModel = hiltViewModel()


) {
    val signUpResult by viewModel.signUpResult.collectAsState(Recomposer.State.Idle)
    val foods by viewModel.signUpResponse.collectAsState()

    var passwordVisibility by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
            elevation = 0.dp,
        ) {
            IconButton(onClick = {
                navigateToProfile()

            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground
                )
            }
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
        }
    }) {
        var valueTextFieldUserName by rememberSaveable {
            mutableStateOf("")
        }
        var valueTextFieldPassword by rememberSaveable {
            mutableStateOf("")
        }
        var valueTextFieldPasswordCheck by rememberSaveable {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(66.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "",
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(80.dp))
                    .size(64.dp)
                    .align(CenterHorizontally)
                    .background(MaterialTheme.colors.primary)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(88.dp))
            Text(
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = stringResource(id = R.string.enter_your_info_to_register),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(43.dp))
            TextField(
                value = valueTextFieldUserName,
                onValueChange = { value ->
                    valueTextFieldUserName = value
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colors.surface)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp, color = Color.Transparent
                    ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.username),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = MaterialTheme.colors.surface,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
                )
            )
            TextField(
                value = valueTextFieldPassword,
                onValueChange = { value ->
                    valueTextFieldPassword = value
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colors.surface)
                    .fillMaxWidth()
                    .align(End)
                    .border(
                        width = 1.dp, color = Color.Transparent
                    ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.password),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = MaterialTheme.colors.surface,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),

                )
            TextField(
                value = valueTextFieldPasswordCheck,
                onValueChange = { value ->
                    valueTextFieldPasswordCheck = value
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colors.surface)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp, color = Color.Transparent
                    ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.repeat_password),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = MaterialTheme.colors.surface,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),

                )
            Button(
                enabled = valueTextFieldPassword.trim().length>=8 && valueTextFieldPassword == valueTextFieldPasswordCheck && valueTextFieldUserName.isNotBlank() && valueTextFieldPasswordCheck.isNotBlank(),
                onClick = {
                    val body= SignUpBody(username = valueTextFieldUserName, password = valueTextFieldPassword)
                    viewModel.postUserSignUp(body)
                    if (signUpResult is Result.Success) {
                        navigateToProfileLogin()
                    }
                    saveUserName(valueTextFieldUserName)
                    isLogin(true)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium

            ) {
                Text(
                    text = stringResource(id = R.string.confirm),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onBackground
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier.padding(end = 2.dp),
                    text = stringResource(id = R.string.not_registered),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    modifier = Modifier.clickable {
                        navigateToProfileLogin()
                    },
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.subtitle1,
                    color = blue
                )
            }
        }
    }
}