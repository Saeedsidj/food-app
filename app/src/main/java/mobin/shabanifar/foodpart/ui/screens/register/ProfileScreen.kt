package mobin.shabanifar.foodpart.ui.screens.register

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.data.fakeFoods
import mobin.shabanifar.foodpart.data.models.Result
import mobin.shabanifar.foodpart.data.models.edit_profile.EditUserBody
import mobin.shabanifar.foodpart.ui.screens.foodDetail.CustomSnackbarHost
import mobin.shabanifar.foodpart.utils.USER_AVATAR_BASE_URL
import mobin.shabanifar.foodpart.utils.isValidPassword
import mobin.shabanifar.foodpart.utils.isValidUser
import mobin.shabanifar.foodpart.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navigateToProfileSignIn: () -> Unit, // Callback for navigating to profile sign-in screen
    viewModel: ProfileViewModel = hiltViewModel(),// ProfileViewModel
) {
    val editUserResponse by viewModel.editUserResponse.collectAsState()
    val userNameFlow by viewModel.userSessionManager.userNameFlow.collectAsState("")
    val userImageFlow by viewModel.userSessionManager.userImageFlow.collectAsState("")
    val userIdFlow by viewModel.userSessionManager.userIdFlow.collectAsState("")
    var showDialogState by remember { mutableStateOf(false) }
    var showChangeUserNameTextField by remember { mutableStateOf(false) }
    var showChangePasswordTextField by remember { mutableStateOf(false) }
    var newUsernameValue by rememberSaveable { mutableStateOf("") }
    var oldPasswordValue by rememberSaveable { mutableStateOf("") }
    var newPasswordValue by rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var newPasswordConfirmationValue by rememberSaveable { mutableStateOf("") }
    val isNewPasswordValid = newPasswordValue.trim().isValidPassword()
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var oldPasswordVisibility by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisibility by rememberSaveable { mutableStateOf(false) }
    var passwordCheckVisibility by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val editUserBody = EditUserBody(username = newUsernameValue.ifBlank { null },
        newpassword = newPasswordValue.ifBlank { null },
        oldPassword = oldPasswordValue.ifBlank { null })

    LaunchedEffect(Unit) {
        viewModel.editUserResult.drop(1).collectLatest { result ->
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
                    if (editUserResponse.data == "success") {
                        Log.d("TAGGG", "$newUsernameValue  ::  $userNameFlow")
                        if (editUserResponse.additionalInfo?.token.isNullOrBlank().not()) {
                            viewModel.editUserToken(
                                token = editUserResponse.additionalInfo?.token.toString()
                            )
                            viewModel.editUsername(
                                userName = newUsernameValue.ifBlank { userNameFlow.orEmpty() },
                            )
                        } else {
                            viewModel.editUsername(
                                userName = newUsernameValue.ifBlank { userNameFlow.orEmpty() },
                            )
                        }
                    }
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
            title = {
                Text(
                    text = stringResource(id = R.string.account),
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onBackground
                )
            }, backgroundColor = MaterialTheme.colors.background, elevation = 0.dp
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Spacer(
                modifier = Modifier.height(40.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if (userNameFlow.isNullOrBlank().not()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("$USER_AVATAR_BASE_URL$userIdFlow/$userImageFlow")
                            .decoderFactory(SvgDecoder.Factory()).build(),
                        contentDescription = "",
                        placeholder = painterResource(id = R.drawable.error_food),
                        error = painterResource(id = R.drawable.place_holder_avatar),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(80.dp))
                            .size(64.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.place_holder_avatar),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(80.dp))
                            .size(64.dp)
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Log.i("TAGGG", userNameFlow.toString())
                Text(
                    text = if (userNameFlow != null) userNameFlow.toString() else stringResource(id = R.string.guest),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.weight(1f))
                if (userNameFlow.isNullOrBlank().not()) {
                    Image(painter = painterResource(
                        id = R.drawable.ic_logout
                    ), contentDescription = "", modifier = Modifier.clickable {
                        showDialogState = true
                    })
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            if (userNameFlow.isNullOrBlank().not()) {
                LazyRowForMoreFood()
                Row(modifier = Modifier
                    .align(Alignment.Start)
                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        showChangeUserNameTextField = !showChangeUserNameTextField
                    }
                    .padding(horizontal = 24.dp)) {
                    Text(
                        text = stringResource(R.string.change_username),
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Icon(
                        modifier = Modifier.rotate(if (showChangeUserNameTextField) 90f else 180f),
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = "",
                        tint = MaterialTheme.colors.onBackground,

                        )
                }
                Spacer(modifier = Modifier.size(12.dp))
                if (showChangeUserNameTextField) {
                    TextField(modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 8.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colors.surface)
                        .fillMaxWidth()
                        .border(
                            width = 1.dp, color = Color.Transparent
                        ), value = newUsernameValue, onValueChange = { value ->
                        newUsernameValue = value
                    }, shape = MaterialTheme.shapes.medium, singleLine = true, placeholder = {
                        Text(
                            text = stringResource(id = R.string.new_username),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface
                        )
                    }, colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = MaterialTheme.colors.surface,
                    ), keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Text
                    )
                    )
                }
                Row(modifier = Modifier
                    .align(Alignment.Start)
                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        showChangePasswordTextField = !showChangePasswordTextField
                    }
                    .padding(horizontal = 24.dp)) {
                    Text(
                        text = stringResource(R.string.change_password),
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Icon(
                        modifier = Modifier.rotate(if (showChangePasswordTextField) 90f else 180f),
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = "",
                        tint = MaterialTheme.colors.onBackground,

                        )
                }
                Spacer(modifier = Modifier.size(12.dp))
                if (showChangePasswordTextField) {
                    Box(Modifier.padding(horizontal = 24.dp)) {
                        TextField(
                            value = oldPasswordValue,
                            onValueChange = { value ->
                                oldPasswordValue = value
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
                                    text = stringResource(id = R.string.current_password),
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
                            visualTransformation = if (oldPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        )
                        IconButton(
                            onClick = {
                                oldPasswordVisibility = !oldPasswordVisibility
                            }, modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 5.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = if (oldPasswordVisibility) R.drawable.noun_visibility else R.drawable.visible),
                                contentDescription = "",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                    Box(Modifier.padding(horizontal = 24.dp)) {
                        TextField(
                            value = newPasswordValue,
                            onValueChange = { value ->
                                newPasswordValue = value
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
                                    text = stringResource(id = R.string.new_password),
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
                            visualTransformation = if (newPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        )
                        IconButton(
                            onClick = {
                                newPasswordVisibility = !newPasswordVisibility
                            }, modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 5.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = if (newPasswordVisibility) R.drawable.noun_visibility else R.drawable.visible),
                                contentDescription = "",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                    Box(Modifier.padding(horizontal = 24.dp)) {
                        TextField(
                            value = newPasswordConfirmationValue,
                            onValueChange = { value ->
                                newPasswordConfirmationValue = value
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
                                    text = stringResource(id = R.string.repeat_new_password),
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
                            visualTransformation = if (passwordCheckVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        )
                        IconButton(
                            onClick = {
                                passwordCheckVisibility = !passwordCheckVisibility
                            }, modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 5.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = if (passwordCheckVisibility) R.drawable.noun_visibility else R.drawable.visible),
                                contentDescription = "",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 16.dp),
            ) {
                Button(
                    // enabled = isButtonEnabled,
                    shape = MaterialTheme.shapes.medium, onClick = {
                        coroutineScope.launch {
                            if (userNameFlow.isNullOrBlank()) {
                                navigateToProfileSignIn()
                            } else if (oldPasswordValue.isBlank() && newUsernameValue.isBlank()) {
                                snackbarHostState.showSnackbar(context.resources.getString(R.string.change_at_least_one_field))
                            } else if (newUsernameValue.isBlank()
                                    .not() && newUsernameValue.trim().isValidUser().not()
                            ) {
                                snackbarHostState.showSnackbar(context.resources.getString(R.string.username_must_be_at_least_four_character_long))
                            } else if ((oldPasswordValue.isBlank()
                                    .not() && oldPasswordValue.trim().isValidPassword().not())
                            ) {
                                snackbarHostState.showSnackbar(context.resources.getString(R.string.current_password_is_not_entered_correctly))
                            } else if (isNewPasswordValid && newPasswordValue != newPasswordConfirmationValue && oldPasswordValue.isBlank()
                                    .not()
                            ) {
                                snackbarHostState.showSnackbar(context.resources.getString(R.string.new_password_does_not_match_the_password))
                            } else if (oldPasswordValue.isBlank()
                                    .not() && newUsernameValue.isBlank().not()
                            ) {
                                snackbarHostState.showSnackbar(context.resources.getString(R.string.enter_new_password))
                            } else if (oldPasswordValue.isBlank()
                                    .not() && isNewPasswordValid.not()
                            ) {
                                snackbarHostState.showSnackbar(context.resources.getString(R.string.new_password_has_not_entered_correctly))
                            } else if (oldPasswordValue.isBlank() && (newPasswordValue.isBlank()
                                    .not() || newPasswordConfirmationValue.isBlank().not())
                            ) {
                                snackbarHostState.showSnackbar(context.resources.getString(R.string.enter_the_current_password))
                            } else {
                                viewModel.postEditUser(editUserBody)
                            }
                        }
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colors.onBackground
                        )
                    } else {
                        Text(
                            text = stringResource(id = if (userNameFlow.isNullOrBlank()) R.string.enter_to else R.string.confirm),
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                }
            }
            //Dialog
            if (showDialogState) {
                Dialog(onDismissRequest = {
                    showDialogState = false
                }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .background(color = MaterialTheme.colors.surface),
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                top = 36.dp, bottom = 24.dp, start = 40.dp, end = 40.dp
                            ),
                            text = stringResource(R.string.confirm_exit),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onBackground
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Button(
                                onClick = {
                                    showDialogState = false
                                    viewModel.clearUserInfo()
                                },
                                Modifier
                                    .weight(1f)
                                    .padding(16.dp),
                                shape = MaterialTheme.shapes.medium,
                            ) {
                                Text(
                                    text = stringResource(R.string.exit),
                                    style = MaterialTheme.typography.button,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                            Button(
                                onClick = {
                                    showDialogState = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Transparent,
                                    contentColor = MaterialTheme.colors.onBackground,
                                ),
                                modifier = Modifier.padding(end = 16.dp),
                                shape = MaterialTheme.shapes.medium,
                                border = BorderStroke(
                                    1.dp, MaterialTheme.colors.primary
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.cancel),
                                    style = MaterialTheme.typography.button,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

//bookmarked food
@Composable
private fun LazyRowForMoreFood(
) {
    val fakeFoodsFiveItem = fakeFoods.subList(fromIndex = 0, toIndex = 4)
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        content = {
            items(fakeFoodsFiveItem) {
                Column(verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .width(136.dp)
                        .height(136.dp)
                        .clickable {}) {
                    Image(
                        painterResource(it.image),
                        contentDescription = "",
                        modifier = Modifier
                            .width(136.dp)
                            .height(80.dp)
                            .clip(shape = MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = "${it.time} دقیقه",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            item {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(136.dp)
                        .height(136.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            10.dp, Alignment.CenterHorizontally
                        ),
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .width(136.dp)
                            .height(80.dp)
                            .background(
                                color = Color(0x4D747474),
                            )
                            .clickable {}) {
                        Text(text = "بیشتر", style = MaterialTheme.typography.body1)
                        Icon(
                            painterResource(R.drawable.ic_back),
                            contentDescription = "",
                            modifier = Modifier.rotate(180f)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                }
            }
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    )
}

