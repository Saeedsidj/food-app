package mobin.shabanifar.foodpart.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import mobin.shabanifar.foodpart.FakeFoods
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.fakeFoods


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Search(
    navToDetail: (Int, String, Int, Int) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var textField by remember { mutableStateOf(TextFieldValue("")) }
    var searchText by remember { mutableStateOf("") }
    var isSearchSuccessful: Boolean? by remember { mutableStateOf(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.what_are_you_looking_for),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.h2
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            TextField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        when (isSearchSuccessful) {
                            true -> MaterialTheme.colors.onBackground
                            false -> MaterialTheme.colors.primary
                            null -> MaterialTheme.colors.surface
                        },
                        MaterialTheme.shapes.medium
                    ),
                value = textField,
                shape = MaterialTheme.shapes.medium,
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                        searchText = textField.text
                    }
                ),
                singleLine = true,

                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,// خط زیر تکست فیلد وقتی روش کلیک بشه
                    unfocusedIndicatorColor = Color.Transparent, // خط زیر تکست فیلد وقتی روش کلیک نشده هنوز
                    backgroundColor = MaterialTheme.colors.surface,
                    cursorColor = Color.Yellow, // رنگ کرسر
                    textColor = if (isSearchSuccessful == true || isSearchSuccessful == null) MaterialTheme.colors.onBackground else MaterialTheme.colors.primary,
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.write_here),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                onValueChange = { newTextValue ->
                    textField = newTextValue
                    // این شرط باعث میشه وقتی متن تکست فیلد خالی بشه خودکار سرچ ریست بشه و تکست فیلد برگرده به حالت اول
                    if (newTextValue.text == "") {
                        searchText = ""
                        isSearchSuccessful = null
                    }
                },
                trailingIcon = {
                    if (/*isSearchSuccessful != null*/textField.text.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                textField = TextFieldValue("")
                                searchText = ""
                                isSearchSuccessful = null
                            })
                        {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "",
                                tint = if (textField.text.isNotEmpty() && isSearchSuccessful != false) MaterialTheme.colors.onBackground else MaterialTheme.colors.primary,
                            )
                        }
                    }
                }
            )
            val foundItems = fakeFoods.filter { items ->
                items.name == searchText
            }
            if (foundItems.isNotEmpty()) {
                isSearchSuccessful = true
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(R.string.search_result, searchText),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )

                SearchedItems(foundItems, navToDetail)
            } else if (searchText != "") {
                isSearchSuccessful = false
                SearchedFailed()
            }

        }
    }
}


@Composable
fun SearchedItems(
    foundItems: List<FakeFoods>,
    navToDetail: (Int, String, Int, Int) -> Unit
) {

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(
            vertical = 16.dp,
            horizontal = 40.dp
        ) // پدینگ آیتم ها با حاشیه = horizontal
    ) {
        items(foundItems) {
            Column(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        navToDetail(it.degree, it.name, it.time, it.image)
                    }
            ) {
                Image(
                    painter = painterResource(id = it.image),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .size(width = 240.dp, height = 85.dp)
                )

                Text(
                    text = it.name,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 4.dp)
                )
                Text(
                    text = stringResource(id = R.string.food_time, it.time),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
        }
    }

}


@Composable
fun SearchedFailed() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.oops_no_results),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }
}