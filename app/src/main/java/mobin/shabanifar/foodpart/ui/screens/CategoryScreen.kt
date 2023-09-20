package mobin.shabanifar.foodpart.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults.chipColors
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.data.database.category.CategoryEntity
import mobin.shabanifar.foodpart.data.database.food.FoodEntity
import mobin.shabanifar.foodpart.data.models.Result
import mobin.shabanifar.foodpart.viewmodel.CategoryViewModel

@Composable
fun DividerOrLoading(
    foodResult: Result,
    categoryResult: Result,
    categoryList: List<CategoryEntity>?,
    foodList: List<FoodEntity>?
) = when (foodResult) {
    Result.Loading -> {
        if (foodList.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .height(0.5.dp)
                    .fillMaxWidth()
            )
        } else {
            Divider(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colors.onSurface
            )
        }
    }

    Result.Success -> {
        if (!categoryList.isNullOrEmpty()) {
            Divider(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colors.onSurface
            )
        } else {
        }
    }

    else -> {
        Divider(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun CategoryScreen(
    navToDetail: (String) -> Unit, viewModel: CategoryViewModel = hiltViewModel()
) {
    val categoryResult by viewModel.categoryResult.collectAsState(Result.Idle)
    val foodResult by viewModel.foodResult.collectAsState(Result.Idle)
    val categoryList by viewModel.categoryList.collectAsState()
    val subList by viewModel.subList.collectAsState()
    val foodList by viewModel.foodList.collectAsState()



    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name_fa),
                    style = MaterialTheme.typography.h1,
                )
            }, backgroundColor = MaterialTheme.colors.background, elevation = 0.dp
        )
    }) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            if (categoryResult == Result.Loading && categoryList.isNullOrEmpty()) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            } else if (categoryResult == Result.Success || !categoryList.isNullOrEmpty()) {

                // نمایش آیتم ها در بالا صفحه
                CategoryItems()

                // ایجاد فاصله بین اسم کتگوری و خط جدا کننده اول
                Spacer(modifier = Modifier.height(8.dp))
                //خط جدا کننده اول
                Divider(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colors.onSurface
                )

            }
            if (!subList.isNullOrEmpty()) {

                //ساب کتگوری ها
                SubCategory()

                DividerOrLoading(foodResult, categoryResult, categoryList, foodList)

            }


            if (!foodList.isNullOrEmpty() || foodResult == Result.Success) {
                FoodItems(navToDetail)
            } else if (foodList.isNullOrEmpty() || foodResult == Result.Success){
                //if (foodResult == Result.Success) {
                    NoFoodFound()
                //}
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryItems(
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val selectedCategoryId by viewModel.selectedCategoryId.collectAsState()
    val categoryList by viewModel.categoryList.collectAsState()

    // نمایش لیست کتگوری ها
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        /*viewModel.getCategoryItems(categoryData)*/
        itemsIndexed(categoryList ?: emptyList()) { index, it ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(64.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        viewModel.updateCategorySelectedId(it.id)
                        //viewModel.setDataOfSelectedCategory(it.id)
                        //if (categoryData?.data?.get(index)?.subCategories.isNullOrEmpty()) {
                        viewModel.getFoodApi(it.id)
                        viewModel.observeSubCategoryByCategoryId(it.id)
                        viewModel.observeFoodByCategoryId(it.id)
                        //}

                    }) {
                // نمایش آیتمی که کلیکشده بصورت رنگی
                val categoryImageModifier = if (it.id == selectedCategoryId) {
                    Modifier
                        .size(64.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.primary,
                            shape = MaterialTheme.shapes.medium
                        )
                } else {
                    Modifier
                        .size(64.dp)
                        .clip(MaterialTheme.shapes.medium)
                }
                val categoryTextColor = if (it.id == selectedCategoryId) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.onBackground
                }
                AsyncImage(
                    model = it.image,
                    error = painterResource(id = R.drawable.category_image_load_failed),
                    placeholder = painterResource(id = R.drawable.image_place_holder_ghavidel),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = categoryImageModifier,
                )

                // فاصله بین عکس کتگوری و نام کتگوری
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .basicMarquee(),
                    text = it.name,
                    style = MaterialTheme.typography.body1,
                    color = categoryTextColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SubCategory(
    viewModel: CategoryViewModel = hiltViewModel()
) {
    // ذخیره ایندکسی که کلیک شده برای رنگی کردن آن
    //val subCategoryList by viewModel.subCategoryList.collectAsState()
    val selectedSubCategoryId by viewModel.selectedSubCategoryId.collectAsState()
    //val selectedCategoryId by viewModel.selectedCategoryId.collectAsState()
    //val catWithSub by viewModel.catWithSub.collectAsState()
    val subList by viewModel.subList.collectAsState()

    LazyRow(
        modifier = Modifier.padding(top = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp), // پدینگ ساب کتگوری از کنار صفحه
        horizontalArrangement = Arrangement.spacedBy(8.dp) // پدینگ بین ساب کتگوری ها
    ) {
        items(subList ?: emptyList()) { item ->
            Chip(border = if (selectedSubCategoryId == item.id) {
                BorderStroke(1.dp, MaterialTheme.colors.primary)
            } else {
                BorderStroke(0.dp, MaterialTheme.colors.surface)
            },
                modifier = Modifier.height(32.dp),
                colors = chipColors(
                    backgroundColor = if (selectedSubCategoryId == item.id) {
                        Color(0x1AFF6262)
                    } else {
                        MaterialTheme.colors.surface
                    },
                    contentColor = MaterialTheme.colors.onBackground,
                ),
                onClick = {
                    viewModel.updateSubCategorySelectedId(item.id)
                    viewModel.getFoodApi(item.id)
                    viewModel.observeFoodByCategoryId(item.id)
                }) {
                val subTextColor = if (item.id == selectedSubCategoryId) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.onBackground
                }
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.subtitle1,
                    color = subTextColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodItems(
    navToDetail: (String) -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val foodList by viewModel.foodList.collectAsState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(
            vertical = 16.dp, horizontal = 40.dp
        ) // پدینگ آیتم ها با حاشیه = horizontal
    ) {
        items(foodList ?: emptyList()) {
            Column(modifier = Modifier
                .padding(bottom = 24.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    navToDetail(it.id)
                }) {

                AsyncImage(
                    model = it.image,
                    error = painterResource(id = R.drawable.no_food_image_found_ghavidel),
                    placeholder = painterResource(id = R.drawable.image_place_holder_ghavidel),
                    contentDescription = null,
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
                        .basicMarquee()
                )

                val time = (it.cookTime ?: 0) + (it.readyTime ?: 0)
                if (time > 0) {
                    Text(
                        text = stringResource(id = R.string.food_time, time),
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }

}


@Composable
fun NoFoodFound() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.no_food_was_found_to_display),
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Button(onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(id = R.string.try_again),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}