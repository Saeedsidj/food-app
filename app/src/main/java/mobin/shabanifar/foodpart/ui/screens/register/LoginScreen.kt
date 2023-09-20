package mobin.shabanifar.foodpart.ui.screens.register


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.data.models.Result
import mobin.shabanifar.foodpart.data.models.sign_up.SignUpBody
import mobin.shabanifar.foodpart.ui.screens.foodDetail.CustomSnackbarHost
import mobin.shabanifar.foodpart.ui.theme.blue
import mobin.shabanifar.foodpart.utils.isValidPassword
import mobin.shabanifar.foodpart.utils.isValidUser
import mobin.shabanifar.foodpart.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navigateToProfileSignIn: () -> Unit, // Callback for navigating to profile sign-in screen
    navigateToProfile: () -> Unit, // Callback for navigating to profile screen
    viewModel: LoginViewModel = hiltViewModel()//LoginViewModel
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    var usernameValue by rememberSaveable { mutableStateOf("") }
    var passwordValue by rememberSaveable { mutableStateOf("") }

    var isLoading by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val loginResponse by viewModel.loginResponse.collectAsState()

    val isPasswordValid = passwordValue.trim().isValidPassword()
    val isUsernameValid = usernameValue.trim().isValidUser()

    LaunchedEffect(Unit) {
        viewModel.loginResult.drop(1).collectLatest { result ->
            when (result) {
                is Result.Error -> {
                    isLoading = false
                    snackbarHostState.showSnackbar(message = result.message)
                }

                Result.Idle, Result.Loading -> {
                    isLoading = true
                }

                Result.Success -> {
                    isLoading = false
                    navigateToProfile()
                    viewModel.saveUserInfo(
                        token = loginResponse.data.token,
                        userName = loginResponse.data.user.username,
                        userImage = loginResponse.data.user.avatar,
                        userId = loginResponse.data.user.id,
                    )
                }
            }
        }
    }
    Scaffold(snackbarHost = {
        CustomSnackbarHost(
            snackbarHostState = snackbarHostState,
        )
    }, topBar = {
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
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp)
                .verticalScroll(
                    rememberScrollState()
                ), verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(66.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "",
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(80.dp))
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally)
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
                text = stringResource(id = R.string.enter_your_info_to_login),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(43.dp))
            TextField(
                value = usernameValue,
                onValueChange = { value ->
                    usernameValue = value
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
            Box {
                TextField(
                    value = passwordValue,
                    onValueChange = { value ->
                        passwordValue = value
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
                            text = stringResource(id = R.string.password_log_in),
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
                IconButton(
                    onClick = {
                        passwordVisibility = !passwordVisibility
                    }, modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = if (passwordVisibility) R.drawable.noun_visibility else R.drawable.visible),
                        contentDescription = "",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            }
            Button(
                enabled = isPasswordValid && isUsernameValid,
                onClick = {
                    val body = SignUpBody(
                        username = usernameValue, password = passwordValue
                    )
                    viewModel.postUserLogin(body)
                },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colors.onBackground
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.confirm),
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier.padding(end = 2.dp),
                    text = stringResource(id = R.string.account_not_registered),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .clickable { navigateToProfileSignIn() },
                    text = stringResource(id = R.string.register),
                    style = MaterialTheme.typography.subtitle1,
                    color = blue
                )
                Text(
                    text = stringResource(id = R.string.doo),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}