package mobin.shabanifar.foodpart.screens


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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.ui.theme.blue

@Composable
fun LoginScreen(
    navigateToProfileSignIn: () -> Unit,
    navigateToProfile: () -> Unit,
    saveUserName: (String) -> Unit,
    isLogin: (Boolean) -> Unit
) {
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
                text = stringResource(id = R.string.login),
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
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
                    .height(56.dp)
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
                    .height(56.dp)
                    .align(Alignment.End)
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
                    imeAction = ImeAction.Done, keyboardType = KeyboardType.NumberPassword
                )
            )
            Button(
                enabled = valueTextFieldUserName.isNotBlank() && valueTextFieldPassword.isNotBlank(),
                onClick = {
                    saveUserName(valueTextFieldUserName)
                    isLogin(true)
                    navigateToProfile()
                },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth()
                    .height(48.dp)
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