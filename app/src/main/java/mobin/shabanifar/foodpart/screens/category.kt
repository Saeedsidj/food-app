package mobin.shabanifar.foodpart.screens

<<<<<<< app/src/main/java/mobin/shabanifar/foodpart/screens/category.kt
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
import androidx.compose.runtime.mutableStateListOf
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
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.categoryListItems
import mobin.shabanifar.foodpart.fakeFoodItems

@Composable
fun Category() {
    // برای نشون دادن صفحه غذایی یافت نشد
    var isFood by remember { mutableStateOf<Boolean>(true) }
    val lambIsFood = { it: Boolean -> isFood = it }
    //
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name_fa),
                        style = MaterialTheme.typography.h1,
                    )
                },
                backgroundColor = MaterialTheme.colors.background
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // نمایش آیتم ها در بالا صفحه
            CategoryItems(lambIsFood)

            //خط جدا کننده اول
            Divider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                //startIndent = 16.dp,
                thickness = 1.dp,
                color = Color.White
            )

            //ساب کتگوری ها
            SubCategory()

            // جدا کننده دوم
            Divider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                thickness = 1.dp,
                color = Color.White
            )

            // برای نمایش لیست غذا ها یا صفحه غذایی نیست
            if (isFood) {
                FoodItems()

            } else {
                NoFood()
            }
        }
    }
}


@Composable
fun CategoryItems(lambIsFood: (Boolean) -> Unit) {
    // لیست آیتم های کتگوری که بالای صفحه نمایش داده میشن
    val categoryListItems = remember {
        categoryListItems
    }

    // ذخیره ایندکس کتگوری ای که انتخاب شده است برای رنگی کردن آن
    var indexCategoryClicked by remember { mutableStateOf<Int>(0) }

    // نمایش لیست کتگوری ها
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        itemsIndexed(categoryListItems) { index, it ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        if (it.name == "غذا نیست") {
                            lambIsFood(false)
                        } else {
                            lambIsFood(true)
                        }
                        indexCategoryClicked = index
                    }
            ) {

                // نمایش آیتمی که کلیکشده بصورت رنگی
                if (index == indexCategoryClicked) {
                    Image(
                        modifier = Modifier
                            .size(64.dp)
                            //.padding(8.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.primary,
                                shape = MaterialTheme.shapes.medium
                            ),
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
                        color = MaterialTheme.colors.primary
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .size(64.dp)
                            //.padding(8.dp)
                            .clip(MaterialTheme.shapes.medium),
                        painter = painterResource(id = it.image),
                        contentScale = ContentScale.Crop,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it.name,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                // ایجاد فاصله بین اسم کتگوری و خط جدا کننده اول
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SubCategory() {
    // ذخیره ایندکسی که کلیک شده برای رنگی کردن آن
    var indexSubCategoryClicked by remember { mutableStateOf<Int>(0) }

    // لیست ساب کتگوری ها - لیست استرینگی ساده
    val subCategoryList = remember {
        mutableStateListOf<String>(
            "آبگوشت",
            "دیزی",
            "اشکنه",
            "آش",
            "آبگوشت",
            "دیزی",
            "اشکنه",
            "آش",
            "آبگوشت",
            "دیزی",
            "اشکنه",
            "آش",
            "آبگوشت",
            "دیزی",
            "اشکنه",
            "آش",

        )
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        itemsIndexed(subCategoryList) { index, it ->
            Chip(
                modifier = Modifier
                    .padding(start = 4.dp),

                shape = MaterialTheme.shapes.medium,
                colors = chipColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onBackground,
                ),
                onClick = {
                    indexSubCategoryClicked = index
                }
            ) {
                if (index == indexSubCategoryClicked) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.primary
                    )
                } else {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }

}


@Composable
fun FoodItems() {

    // فیک لیست فود آیتم
    var fakeFoodItems = remember {
        fakeFoodItems
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 40.dp, end = 40.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(fakeFoodItems) {
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
                        //.border(1.dp, Color.Black)
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

                onClick = { /*TODO*/ }
            ) {
                Text(

                    text = stringResource(id = R.string.try_again),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}