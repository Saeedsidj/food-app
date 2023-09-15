package mobin.shabanifar.foodpart.ui.screens.what_to_cook

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import mobin.shabanifar.foodpart.data.LevelState
import mobin.shabanifar.foodpart.ui.theme.green

@Composable
fun WhatToCookFormScreen(
    // Callback for navigating to what to cook list screen
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

        var valueTextWhatDoYouHave by rememberSaveable { mutableStateOf("") }
        var valueTextHowMuchTimeDoYouHave by rememberSaveable { mutableStateOf("") }
        var selectedLevel by rememberSaveable { mutableStateOf("") }
        var isShowVisible by rememberSaveable { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
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
                        .padding(bottom = 16.dp)
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
            Box(
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                TextField(
                    value = valueTextHowMuchTimeDoYouHave,
                    onValueChange = { value ->
                        if (value.length <= 4)
                            valueTextHowMuchTimeDoYouHave = value
                    }, shape = MaterialTheme.shapes.medium, modifier = Modifier
                        .background(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colors.surface
                        )
                        .fillMaxWidth()
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

                CustomRadioButton(selectedLevel) {
                    selectedLevel = it
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                enabled = valueTextWhatDoYouHave.isNotBlank() && valueTextHowMuchTimeDoYouHave.isNotBlank() && valueTextWhatDoYouHave.trim().length >= 3,
                onClick = {
                    navigateToWTCList(
                        valueTextWhatDoYouHave, valueTextHowMuchTimeDoYouHave, selectedLevel
                    )
                },
                modifier = Modifier
                    .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium

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

@Composable
fun CustomRadioButton(
    selectedLevel: String,
    changeSelectedLevel: (String) -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val radioButtonItemsList =
        listOf(LevelState.Any, LevelState.Easy, LevelState.Medium, LevelState.Hard)
    radioButtonItemsList.forEach { item ->
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .clickable(
                interactionSource = interactionSource, indication = null
            ) {
                changeSelectedLevel(item.id)
            }
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)) {
            Icon(
                painter = painterResource(if (selectedLevel == item.id) R.drawable.check_circle_outline else R.drawable.uncheck_circle_outline),
                contentDescription = "",
                tint = (if (selectedLevel == item.id) green else MaterialTheme.colors.onBackground)
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 2.dp, end = 5.dp),
                text = item.name
            )
        }
    }
}