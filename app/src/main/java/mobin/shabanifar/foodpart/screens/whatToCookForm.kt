package mobin.shabanifar.foodpart.screens

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
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
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.data.LevelRadioBtnState
import mobin.shabanifar.foodpart.ui.theme.green

@Composable
fun WhatToCook(
    navigateToWTCList: (whatDoYouHave: String, howMuchTimeHave: String, level: String) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
            elevation = 0.dp,
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = R.string.what_should_i_cook),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
        }
    }) {
        var valueTextWhatDoYouHave by rememberSaveable {
            mutableStateOf("")
        }
        var valueTextHowMuchTimeDoYouHave by rememberSaveable {
            mutableStateOf("")
        }
        var selectedLevel by rememberSaveable { mutableStateOf(LevelRadioBtnState.NO_MATTER) }

        var isShowVisible by rememberSaveable { mutableStateOf(true) }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), verticalArrangement = Arrangement.Top
        ) {
            if (isShowVisible) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colors.surface)
                        .fillMaxWidth()
                        .height(156.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.help),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onBackground
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            modifier = Modifier.clickable {
                                isShowVisible = false
                            },
                            painter = painterResource(id = R.drawable.ic_clear),
                            contentDescription = ""
                        )

                    }
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = stringResource(id = R.string.instruction),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
            TextField(
                value = valueTextWhatDoYouHave,
                onValueChange = { value ->
                    valueTextWhatDoYouHave = value
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
                    .background(
                        shape = MaterialTheme.shapes.medium, color = MaterialTheme.colors.surface
                    )
                    .fillMaxWidth()
                    .height(64.dp)
                    .border(
                        width = 1.dp, color = Color.Transparent
                    ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.what_do_you_have),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = MaterialTheme.colors.surface,
                    textColor = MaterialTheme.colors.onBackground
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
                )
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
                text = stringResource(id = R.string.separate_with_comma),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Box(modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp)) {
                TextField(
                    value = valueTextHowMuchTimeDoYouHave, onValueChange = { value ->
                        valueTextHowMuchTimeDoYouHave = value
                    }, shape = MaterialTheme.shapes.medium, modifier = Modifier
                        .background(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colors.surface
                        )
                        .fillMaxWidth()
                        .height(64.dp)
                        .border(
                            width = 1.dp, color = Color.Transparent
                        ), singleLine = true, placeholder = {
                        Text(
                            text = stringResource(id = R.string.how_much_time_do_you_have),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface
                        )
                    }, colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = MaterialTheme.colors.surface,
                        textColor = MaterialTheme.colors.onBackground
                    ), keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.NumberPassword
                    )
                )
                Text(
                    modifier = Modifier
                        .align(CenterEnd)
                        .padding(end = 16.dp, top = 12.dp, bottom = 12.dp),
                    text = stringResource(id = R.string.min),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,

                    )
            }
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = stringResource(id = R.string.recipe_difficulty),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                IconToggleButton(checked = selectedLevel == LevelRadioBtnState.NO_MATTER,
                    onCheckedChange = { selectedLevel = LevelRadioBtnState.NO_MATTER }) {
                    Icon(
                        painter = painterResource(if (selectedLevel == LevelRadioBtnState.NO_MATTER) R.drawable.check_circle_outline else R.drawable.uncheck_circle_outline),
                        contentDescription = "Radio button icon",
                        tint = (if (selectedLevel == LevelRadioBtnState.NO_MATTER) green else MaterialTheme.colors.onBackground)
                    )
                }
                Text(
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(start = 2.dp, end = 5.dp),
                    text = stringResource(id = R.string.any)
                )
                IconToggleButton(checked = selectedLevel == LevelRadioBtnState.EASY,
                    onCheckedChange = { selectedLevel = LevelRadioBtnState.EASY }) {
                    Icon(
                        painter = painterResource(if (selectedLevel == LevelRadioBtnState.EASY) R.drawable.check_circle_outline else R.drawable.uncheck_circle_outline),
                        contentDescription = "Radio button icon",
                        tint = (if (selectedLevel == LevelRadioBtnState.EASY) green else MaterialTheme.colors.onBackground)
                    )
                }
                Text(
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(start = 2.dp, end = 5.dp),
                    text = stringResource(id = R.string.easy)
                )
                IconToggleButton(checked = selectedLevel == LevelRadioBtnState.MEDIUM,
                    onCheckedChange = { selectedLevel = LevelRadioBtnState.MEDIUM }) {
                    Icon(
                        painter = painterResource(if (selectedLevel == LevelRadioBtnState.MEDIUM) R.drawable.check_circle_outline else R.drawable.uncheck_circle_outline),
                        contentDescription = "Radio button icon",
                        tint = (if (selectedLevel == LevelRadioBtnState.MEDIUM) green else MaterialTheme.colors.onBackground)
                    )
                }
                Text(
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(start = 2.dp, end = 5.dp),
                    text = stringResource(id = R.string.medium)
                )
                IconToggleButton(checked = selectedLevel == LevelRadioBtnState.HARD,
                    onCheckedChange = { selectedLevel = LevelRadioBtnState.HARD }) {
                    Icon(
                        painter = painterResource(if (selectedLevel == LevelRadioBtnState.HARD) R.drawable.check_circle_outline else R.drawable.uncheck_circle_outline),
                        contentDescription = "Radio button icon",
                        tint = (if (selectedLevel == LevelRadioBtnState.HARD) green else MaterialTheme.colors.onBackground)
                    )
                }
                Text(
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(start = 2.dp, end = 5.dp),
                    text = stringResource(id = R.string.hard)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                enabled = valueTextWhatDoYouHave.isNotBlank(),
                onClick = {
                    navigateToWTCList(
                        valueTextWhatDoYouHave, valueTextHowMuchTimeDoYouHave, selectedLevel
                    )
                },
                modifier = Modifier
                    .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
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
        }
    }
}