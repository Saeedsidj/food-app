package mobin.shabanifar.foodpart.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.data.fakeFoods

@Composable
fun ShowFoodByAttributesScreen(
    topTitle: String, navToDetail: (Int, String, Int, Int) -> Unit,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                    Text(
                        text = stringResource(R.string.cook_in_minutes, topTitle),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.h2
                    )
                }
            }
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(
                vertical = 16.dp,
                horizontal = 40.dp
            ) // پدینگ آیتم ها با حاشیه = horizontal
        ) {
            items(fakeFoods) {
                Column(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            navToDetail(it.degree, it.name, it.time, it.image)
                        }
                ) {
                    Image(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .size(width = 240.dp, height = 85.dp),
                        painter = painterResource(id = it.image),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.food_time, it.time),
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}