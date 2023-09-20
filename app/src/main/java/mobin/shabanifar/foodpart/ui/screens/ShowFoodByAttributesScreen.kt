package mobin.shabanifar.foodpart.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.data.fakeFoods
import mobin.shabanifar.foodpart.viewmodel.FoodDetailViewModel
import mobin.shabanifar.foodpart.data.models.Result

@Composable
fun ShowFoodByAttributesScreen(
    navToDetail: (foodId:String) -> Unit,
    navController: NavHostController,
    foodDetailViewModel: FoodDetailViewModel= hiltViewModel()
) {
    val foodsByMeal by foodDetailViewModel.foodsByMeal.collectAsState()
    val result by foodDetailViewModel.foodDetailResult.collectAsState(Result.Idle)
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
                }
            }
        }
    ) {
        if (result == Result.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                )
            }
        } else if (result == Result.Success) {
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
                items(foodsByMeal?.data ?: emptyList()) {
                    Column(
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .clickable {
                                navToDetail(it.id)
                            }
                    ) {
                        AsyncImage(
                            model = it.image,
                            contentDescription = it.name,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .size(width = 136.dp, height = 75.dp)
                        )
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                        )
                        if ((it.cookTime?.plus(it.readyTime ?: 0) ?: 0) > 0)
                            Text(
                                text = "${it.readyTime?.plus(it.cookTime?:0)} دقیقه",
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                    }
                }
            }

        }
    }
}