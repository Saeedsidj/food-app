package mobin.shabanifar.foodpart.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import mobin.shabanifar.foodpart.R

@Composable
fun ProfileScreen(
    navigateToProfileSignIn: () -> Unit,
    changeLoginState: () -> Unit,
    usernameSave: String,
    isLogin: Boolean,
) {
    var showDialogState by remember { mutableStateOf(false) }

    Scaffold(topBar = {
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
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.place_holder_avatar),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(80.dp))
                        .size(64.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = if (usernameSave.isEmpty()
                            .not() && isLogin
                    ) usernameSave else stringResource(id = R.string.guest),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.weight(1f))
                if (isLogin) {
                    Image(painter = painterResource(
                        id = R.drawable.ic_logout
                    ), contentDescription = "", modifier = Modifier.clickable {
                        showDialogState = true
                    })
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                shape = MaterialTheme.shapes.medium, onClick = {
                    navigateToProfileSignIn()
                }, modifier = Modifier.fillMaxWidth(), enabled = !isLogin
            ) {
                Text(
                    text = if (!isLogin) stringResource(id = R.string.enter_to) else stringResource(
                        id = R.string.confirm
                    ),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onBackground
                )
            }
            if (showDialogState) {
                Dialog(onDismissRequest = {
                    showDialogState = false
                }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            //.fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .background(color = MaterialTheme.colors.surface)
                            .width(294.dp)
                            .height(180.dp),
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
                                    changeLoginState()
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