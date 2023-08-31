package mobin.shabanifar.foodpart.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mobin.shabanifar.foodpart.R

@Composable
fun Category() {

    var isFood by remember { mutableStateOf<Boolean>(true) }
    val lambIsFood = { it: Boolean ->
        isFood = it
    }
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

            CategoryItems(lambIsFood)

            Divider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                //startIndent = 16.dp,
                thickness = 1.dp,
                color = Color.White
            )

            SubCategory()
            //Spacer(modifier = Modifier.height(4.dp))
            Divider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                thickness = 1.dp,
                color = Color.White
            )
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
    val categoryListItems = remember {
        mutableStateListOf<CategoryItems>(
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "غذا نیست", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
            CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        )
    }
    var indexCategoryClicked by remember { mutableStateOf<Int>(0) }
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
                    //.border(1.dp, Color.Red,MaterialTheme.shapes.medium)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        if (it.name == "غذا نیست") {
                            lambIsFood(false)
                        }else{
                            lambIsFood(true)
                        }
                        indexCategoryClicked = 0
                        categoryListItems[index] =
                            categoryListItems[index].copy(isClicked = !it.isClicked)
                        indexCategoryClicked = index
                    }
            ) {

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

                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SubCategory() {
    var indexSubCategoryClicked by remember { mutableStateOf<Int>(0) }
    val subCategoryList = remember {
        mutableStateListOf<String>(
            "hsbd",
            "djvb",
            "kshdb",
            "kshvbhmsv",
            "aldjvbksd",
            "kahvbk",
            "ljbdd",
            "kahbjhasv",
            "kugakhb"
        )
    }
    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        itemsIndexed(subCategoryList) { index, it ->
            Chip(
                modifier = Modifier.padding(start = 4.dp),
                shape = MaterialTheme.shapes.medium,
                colors = chipColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onBackground,
                ),
                onClick = {
                    indexSubCategoryClicked = 0
                    /*subCategoryList[index] =
                        subCategoryList[index].copy(isCategoryItem = !it.isClicked)*/
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
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 40.dp, end = 40.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(fakeItems) {
            Column(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { /*TODO*/ }

                //.padding(end = 10.dp,start = 10.dp, bottom = 0.dp)
            ) {
                Image(
                    painter = painterResource(id = it.image),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    //contentScale=ContentScale.FillBounds,
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


val itemImage = R.drawable.food_itemj

data class ItemFakeData(val image: Int, val name: String, val time: Int)

val fakeItems = listOf<ItemFakeData>(
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی", time = 15),
    ItemFakeData(image = itemImage, name = "سیب زمینی2", time = 15),
)

data class CategoryItems(val name: String, val image: Int, var isClicked: Boolean = false)

