package mobin.shabanifar.foodpart.ui.screens.what_to_cook

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.data.models.Result
import mobin.shabanifar.foodpart.data.models.what_to_cook_response.WhatToCookResponse
import mobin.shabanifar.foodpart.ui.theme.loading_background
import mobin.shabanifar.foodpart.viewmodel.WhatToCookListViewModel


@Composable
fun WhatToCookListScreen(
    whatDoYouHave: String, // Callback get whatDoYouHave
    howMuchTimeHave: String, // Callback get howMuchTimeHave
    level: String, // Callback get level
    navigateToWTCForm: () -> Unit, // Callback for navigating to what to cook form screen
    navToDetail: (Int, String, Int, Int) -> Unit,// Callback for navigating to detail screen
    viewModel: WhatToCookListViewModel = hiltViewModel()
) {
    val lazyState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    val isTop by remember { derivedStateOf { lazyState.firstVisibleItemIndex != 0 } }
    val quoteResult by viewModel.whatToCookResult.collectAsState(Recomposer.State.Idle)
    val foods by viewModel.whatToCookResponse.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.whatToCookResult.onEach {
            if (quoteResult is Result.Error) {

            }
        }.launchIn(this)
    }

    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
            elevation = 0.dp,
        ) {
            IconButton(onClick = {
                navigateToWTCForm()
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground
                )
            }
            Text(
                text = stringResource(id = R.string.what_should_i_cook),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
        }
    }, floatingActionButton = {
        if (isTop) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        lazyState.animateScrollToItem(0)
                    }
                }, backgroundColor = MaterialTheme.colors.primary
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_forward),
                    contentDescription = ""
                )
            }
        }
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (quoteResult is Result.Loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    backgroundColor = loading_background,
                    color = MaterialTheme.colors.primary
                )
            }
            if (quoteResult is Result.Error) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.error_communicating_with_server),
                        style = MaterialTheme.typography.h3,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(
                        onClick = {
                            viewModel.getWhatToCook(
                                viewModel.whatToCookQueries(
                                    ingredients = whatDoYouHave,
                                    timeLimit = howMuchTimeHave,
                                    difficulty = level
                                )
                            )
                        }, shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = stringResource(id = R.string.try_again),
                            style = MaterialTheme.typography.button,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.onBackground,
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = setString(
                    whatDoYouHave = whatDoYouHave,
                    howMuchTimeHave = howMuchTimeHave,
                    level = when (level) {
                        "1" -> stringResource(id = R.string.easy)
                        "2" -> stringResource(id = R.string.medium)
                        "3" -> stringResource(id = R.string.hard)
                        else -> ""
                    }

                ),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground
            )
            if (quoteResult is Result.Success) {
                SearchedItems(
                    state = lazyState,
                    navToDetail = navToDetail,
                    foods = foods,
                    howMuchTimeHave = howMuchTimeHave,
                    level = level,
                    whatDoYouHave = whatDoYouHave,
                    viewModel = viewModel
                )

            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchedItems(
    state: LazyGridState,
    navToDetail: (Int, String, Int, Int) -> Unit,
    foods: List<WhatToCookResponse.Data>,
    viewModel: WhatToCookListViewModel = hiltViewModel(),
    whatDoYouHave: String, // Callback get whatDoYouHave
    howMuchTimeHave: String, // Callback get howMuchTimeHave
    level: String, // Callback get level
) {
    if (foods.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.no_food_was_found_to_display),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(16.dp)
            )
            Button(
                onClick = {
                    viewModel.getWhatToCook(
                        viewModel.whatToCookQueries(
                            ingredients = whatDoYouHave,
                            timeLimit = howMuchTimeHave,
                            difficulty = level
                        )
                    )
                }, shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(id = R.string.try_again),
                    style = MaterialTheme.typography.button,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground,
                )
            }
        }
    }
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        state = state,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 40.dp)
    ) {
        items(foods) { food ->
            Column(modifier = Modifier
                .padding(bottom = 24.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    //navToDetail(it.degree, it.name, it.time, it.image)
                }) {
                AsyncImage(
                    model = food.image,
                    contentDescription = "",
                    error = painterResource(R.drawable.error_food),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.place_holder_food),
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .size(width = 240.dp, height = 85.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 4.dp)
                        .basicMarquee(),
                    text = food.name,
                    maxLines = 1,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,


                    )
                Text(
                    text = "زمان : " + "${food.cookTime + food.readyTime}" + " دقیقه",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

//Create String for show in top of screen
fun setString(whatDoYouHave: String, howMuchTimeHave: String, level: String): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("نتایج جستجو با ")
    stringBuilder.append("${whatDoYouHave.replace("،", " و ")} \n")
    stringBuilder.append("در $howMuchTimeHave دقیقه ")
    if (level.isNotEmpty()) stringBuilder.append("با درجه سختی $level") else stringBuilder.append("")
    return stringBuilder.toString()
}
