package mobin.shabanifar.foodpart.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults.chipColors
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.data.categoryItems
import mobin.shabanifar.foodpart.data.fakeFoods
import mobin.shabanifar.foodpart.data.subCategoryList

@Composable
fun Category(
    navToDetail: (Int, String, Int, Int) -> Unit
) {
    // برای نشون دادن صفحه غذایی یافت نشد
    var isFood by rememberSaveable { mutableStateOf(true) }
    val lambIsFood = { it: Boolean -> isFood = it }

    var isSub by rememberSaveable { mutableStateOf(true) }
    val lambIsSub = { it: Boolean -> isSub = it }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name_fa),
                        style = MaterialTheme.typography.h1,
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // نمایش آیتم ها در بالا صفحه
            CategoryItems(lambIsFood, lambIsSub)

            // ایجاد فاصله بین اسم کتگوری و خط جدا کننده اول
            Spacer(modifier = Modifier.height(8.dp))

            //خط جدا کننده اول
            Divider(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .height(0.5.dp),
                thickness = 1.dp,
                color = MaterialTheme.colors.onSurface
            )

            if (isSub) {
                //ساب کتگوری ها
                SubCategory()

                // جدا کننده دوم
                Divider(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                        .height(0.5.dp),
                    thickness = 1.dp, color = MaterialTheme.colors.onSurface
                )
            } else {
                if (isFood) {
                    FoodItems(navToDetail)
                }
            }
            // برای نمایش لیست غذا ها یا صفحه غذایی نیست
            if (isFood) {
                FoodItems(navToDetail)

            } else {
                NoFood()
            }
        }
    }
}


@Composable
fun CategoryItems(
    lambIsFood: (Boolean) -> Unit,
    lambIsSub: (Boolean) -> Unit,
) {

    // ذخیره ایندکس کتگوری ای که انتخاب شده است برای رنگی کردن آن
    var indexCategoryClicked by rememberSaveable { mutableIntStateOf(0) }

    // نمایش لیست کتگوری ها
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(categoryItems) { index, it ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(64.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        when (it.name) {
                            "بی ساب" -> {
                                lambIsSub(false)
                                lambIsFood(true)
                            }

                            "غذا نیست" -> {
                                lambIsFood(false)
                                lambIsSub(false)
                            }

                            else -> {
                                lambIsFood(true)
                                lambIsSub(true)
                            }
                        }
                        indexCategoryClicked = index
                    }
            ) {

                // نمایش آیتمی که کلیکشده بصورت رنگی
                val categoryImageModifier = if (index == indexCategoryClicked) {
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
                val categoryTextColor = if (index == indexCategoryClicked) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.onBackground
                }

                Image(
                    modifier = categoryImageModifier,
                    painter = painterResource(id = it.image),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )

                // فاصله بین عکس کتگوری و نام کتگوری
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
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
fun SubCategory() {
    // ذخیره ایندکسی که کلیک شده برای رنگی کردن آن
    var indexSubCategoryClicked by rememberSaveable { mutableIntStateOf(0) }

    LazyRow(
        modifier = Modifier.padding(top = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp), // پدینگ ساب کتگوری از کنار صفحه
        horizontalArrangement = Arrangement.spacedBy(8.dp) // پدینگ بین ساب کتگوری ها
    ) {
        itemsIndexed(subCategoryList) { index, item ->
            Chip(
                border = if (indexSubCategoryClicked == index) {
                    BorderStroke(1.dp, MaterialTheme.colors.primary)
                } else {
                    BorderStroke(0.dp, MaterialTheme.colors.surface)
                },
                modifier = Modifier.height(32.dp),
                shape = MaterialTheme.shapes.medium,
                colors = chipColors(
                    backgroundColor = if (indexSubCategoryClicked == index) {
                        Color(0x1AFF6262)
                    } else {
                        MaterialTheme.colors.surface
                    },
                    contentColor = MaterialTheme.colors.onBackground,
                ),
                onClick = {
                    indexSubCategoryClicked = index
                }
            ) {
                val subTextColor = if (index == indexSubCategoryClicked) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.onBackground
                }
                Text(
                    text = item,
                    style = MaterialTheme.typography.subtitle1,
                    color = subTextColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}


@Composable
fun FoodItems(navToDetail: (Int, String, Int, Int) -> Unit) {

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
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


@Composable
fun NoFood() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
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
            Button(
                onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(id = R.string.try_again),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}
