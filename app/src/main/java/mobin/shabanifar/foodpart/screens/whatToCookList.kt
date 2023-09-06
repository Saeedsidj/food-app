package mobin.shabanifar.foodpart.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.fakeFoodItems

@Composable
fun WhatToCookListScreen(
    whatDoYouHave: String?, howMuchTimeHave: String?, level: String?, navigateToWTCForm: () -> Unit
) {
    val lazyState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    val isTop by remember { derivedStateOf { lazyState.firstVisibleItemIndex != 0 } }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
                elevation = 0.dp,
            ) {
                IconButton(onClick = {
                    navigateToWTCForm()
                }) {
                    Icon(painter = painterResource(R.drawable.ic_back), contentDescription = "")
                }
                Text(
                    text = stringResource(id = R.string.what_should_i_cook),
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onBackground
                )
            }
        },
        floatingActionButton = {
            if (isTop) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            lazyState.animateScrollToItem(0)
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = ""
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {


            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = setString(
                    whatDoYouHave = whatDoYouHave,
                    howMuchTimeHave = howMuchTimeHave,
                    level = level
                ),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground
            )
            SearchedItems(lazyState)
        }
    }
}

@Composable
fun SearchedItems(state: LazyGridState) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        state = state,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 40.dp)
    ) {
        val foundItems = fakeFoodItems.filter { items ->
            true
        }
        items(foundItems) {
            Column(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { /*TODO*/ }
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
                    text = "زمان : " + "${it.time}" + " دقیقه",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

fun setString(whatDoYouHave: String?, howMuchTimeHave: String?, level: String?): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("نتایج جستجو با ")
    whatDoYouHave?.let { stringBuilder.append("${it.replace("،", " و ")} \n") }
    if (howMuchTimeHave.isNullOrEmpty()
            .not()
    ) stringBuilder.append("در $howMuchTimeHave دقیقه ") else stringBuilder.append("")
    if (level.isNullOrEmpty()
            .not() && level != "مهم نیست"
    ) stringBuilder.append("با درجه سختی $level") else stringBuilder.append("")
    return stringBuilder.toString()
}
