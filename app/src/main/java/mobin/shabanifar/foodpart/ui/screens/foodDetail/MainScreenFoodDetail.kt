package mobin.shabanifar.foodpart.ui.screens.foodDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.utils.NavigationBottom
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.data.detailList
import mobin.shabanifar.foodpart.data.fakeFoods
import mobin.shabanifar.foodpart.ui.theme.green
import mobin.shabanifar.foodpart.ui.theme.red
import mobin.shabanifar.foodpart.ui.theme.yellow


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    toAttributesScreen: (String) -> Unit,
    it: PaddingValues,
    scrollState: ScrollState,
    navController: NavHostController,
    tabIndex: Int,
    pagerState: PagerState,
    tabData: List<String>,
    coroutineScope: CoroutineScope,
    degree: Int,
    name: String,
    time: Int,
    image: Int
) {
    Column(
        Modifier
            .padding(start = 13.dp, end = 13.dp)
            .fillMaxSize()
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        ImageFood(navController, image)
        AttributeRow(navController, toAttributesScreen = toAttributesScreen, degree, name, time)
        TabRowDescription(tabIndex, pagerState, tabData, coroutineScope)
        LazyRowForMoreFood(
            navToDetail = { degree: Int, name: String, time: Int, image: Int ->
                navController.navigate("foodDetail/$degree/$name/$time/$image") {
                    launchSingleTop = true
                    restoreState = true
                }
            }, toAttributesScreen = toAttributesScreen
        )
    }
}

@Composable
private fun ImageFood(navController: NavHostController, image: Int) {
    Image(
        painterResource(image),
        contentDescription = "",
        modifier = Modifier
            .height(244.dp)
            .width(380.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable {
                navController.navigate(NavigationBottom.FoodPhoto.route)
            },
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun AttributeRow(
    navController: NavHostController,
    toAttributesScreen: (String) -> Unit,
    degree: Int,
    name: String,
    time: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.End,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "برای 4 نفر",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(
            10.dp, Alignment.CenterHorizontally
        ),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(86.dp)
                .height(32.dp)
                .background(
                    color = Color(0x1AFF6262),
                )
                .clickable {
                    toAttributesScreen("$time دقیقه")
                }) {
            Image(painterResource(R.drawable.time), contentDescription = "")
            Text(text = "$time دقیقه", style = MaterialTheme.typography.caption)
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(text = "صبحانه",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(80.dp)
                .height(32.dp)
                .background(
                    color = Color(0xFF222228)
                )
                .clickable {}
                .padding(5.dp),
            style = MaterialTheme.typography.caption)
        Text(text = "نهار",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(26.dp))
                .width(80.dp)
                .height(32.dp)
                .background(
                    color = Color(0xFF222228),
                )
                .clickable {}
                .padding(5.dp),
            style = MaterialTheme.typography.caption)
        Spacer(modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                10.dp, Alignment.CenterHorizontally
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .border(
                    1.dp, color = when (degree) {
                        1 -> red
                        2 -> yellow
                        3 -> green
                        else -> Color.Transparent
                    }, shape = MaterialTheme.shapes.medium
                )
                .width(80.dp)
                .height(32.dp)
                .background(
                    color = when (degree) {
                        1 -> Color(0x1AFF4444)
                        2 -> Color(0x26FFE100)
                        3 -> Color(0x1A00FF67)
                        else -> Color.Transparent
                    }
                )
                .clickable {}
                .padding(5.dp)) {
            Icon(
                painterResource(id = R.drawable.level),
                contentDescription = "",
                tint = when (degree) {
                    1 -> red
                    2 -> yellow
                    3 -> green
                    else -> Color.Transparent
                }
            )
            Text(
                text = when (degree) {
                    1 -> "دوشوار"
                    2 -> "متوسط"
                    3 -> "آسان"
                    else -> ""
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun TabRowDescription(
    tabIndex: Int, pagerState: PagerState, tabData: List<String>, coroutineScope: CoroutineScope
) {
    TabRow(modifier = Modifier
        .height(40.dp)
        .fillMaxWidth(0.7f),
        backgroundColor = MaterialTheme.colors.background,
        selectedTabIndex = tabIndex,
        indicator = {
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(it[pagerState.currentPage])
                    .height(2.dp)
                    .background(MaterialTheme.colors.primary)
            )
        },
        divider = {}) {
        tabData.forEachIndexed { index, text ->
            Tab(selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onBackground,
                selected = tabIndex == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.h3,
                    )
                },
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally, true)
            )
        }
    }
    HorizontalPager(
        modifier = Modifier
            .height(230.dp)
            .background(
                Color(0xFF222228), shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp), state = pagerState, verticalAlignment = Alignment.Top
    ) { index ->
        Text(
            text = when (index) {
                0 -> detailList.map { it.material }.joinToString { it }
                1 -> detailList.map { it.recipie }.joinToString { it }
                2 -> detailList.map { it.moreInfo }.joinToString { it }
                else -> ""
            },
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
private fun LazyRowForMoreFood(
    navToDetail: (Int, String, Int, Int) -> Unit, toAttributesScreen: (String) -> Unit
) {
    Text(
        text = "بیشتر از این دسته بندی",
        style = MaterialTheme.typography.h3,
        color = MaterialTheme.colors.onBackground,
    )
    val fakeFoodsFiveItem = fakeFoods.subList(fromIndex = 0, toIndex = 4)
    LazyRow(
        content = {
            items(fakeFoodsFiveItem) {
                Column(verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .width(136.dp)
                        .height(136.dp)
                        .clickable {
                            navToDetail(it.degree, it.name, it.time, it.image)
                        }) {
                    Image(
                        painterResource(it.image),
                        contentDescription = "",
                        modifier = Modifier
                            .width(136.dp)
                            .height(80.dp)
                            .clip(shape = MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "${it.name}",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = "${it.time} دقیقه",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            item {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(136.dp)
                        .height(136.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            10.dp, Alignment.CenterHorizontally
                        ),
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .width(136.dp)
                            .height(80.dp)
                            .background(
                                color = Color(0x4D747474),
                            )
                            .clickable {
                                toAttributesScreen("بیشتر از این دسته")
                            }) {
                        Text(text = "بیشتر", style = MaterialTheme.typography.body1)
                        Icon(
                            painterResource(R.drawable.ic_back),
                            contentDescription = "",
                            modifier = Modifier.rotate(180f)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                }
            }
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    )
}
