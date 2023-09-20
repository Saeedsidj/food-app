package mobin.shabanifar.foodpart.ui.screens.foodDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.data.detailList
import mobin.shabanifar.foodpart.ui.theme.lightRed
import mobin.shabanifar.foodpart.viewmodel.FoodDetailViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    it: PaddingValues,
    coroutineScope: CoroutineScope,
    navToImage: (imageUrl: String) -> Unit,
    navToDetail:(id:String)->Unit
) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .padding(it)
            .fillMaxSize()
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start
    ) {
        ImageFood(navToImage = navToImage)
        AttributeRow()
        TabRowDescription(coroutineScope)
        LazyRowForMoreFood(navToDetail)
    }
}

@Composable
private fun ImageFood(
    navToImage: (imageUrl: String) -> Unit,
    foodDetailViewModel: FoodDetailViewModel = hiltViewModel()
) {
    val foodDetail = foodDetailViewModel.foodDetailData.collectAsState()
    val imageUrl = foodDetail.value?.data?.image
    AsyncImage(
        model = imageUrl,
        contentDescription = "",
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(244.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable {
                navToImage(imageUrl ?: "")
            },
        contentScale = ContentScale.Crop
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun AttributeRow(
    foodDetailViewModel: FoodDetailViewModel = hiltViewModel(),
) {
    val foodDetailData by foodDetailViewModel.foodDetailData.collectAsState()
    val totalTime = (foodDetailData?.data?.readyTime ?: 0) + (foodDetailData?.data?.cookTime ?: 0)
    val mealsList = foodDetailViewModel.getMeals()
    val difficultyName = foodDetailViewModel.getDifficultyName()
    val difficultyColor = foodDetailViewModel.getDifficultyColor()
    val count = foodDetailViewModel.getCount()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 16.dp),

        ) {
        Text(
            text = foodDetailData?.data?.name ?: "",
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .basicMarquee()
                .padding(start = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.weight(1f))
        if (count.isNotEmpty())
            Text(
                text = stringResource(id = R.string.count).plus(count),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )
        if (totalTime > 0) {
            Chip(
                onClick = { },
                colors = ChipDefaults.chipColors(
                    backgroundColor = lightRed,
                    contentColor = MaterialTheme.colors.onBackground,
                )
            ) {
                Image(painterResource(R.drawable.time), contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "$totalTime دقیقه", style = MaterialTheme.typography.caption)
            }

        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        LazyRow() {
            items(mealsList) {
                Chip(
                    onClick = { /*TODO*/ },
                    Modifier.width(80.dp),
                    colors = ChipDefaults.chipColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor = MaterialTheme.colors.onBackground,
                    )
                ) {
                    Text(
                        text = it.name,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Chip(
            onClick = { },
            colors = ChipDefaults.chipColors(
                backgroundColor = difficultyColor.copy(alpha = 0.1f),
                contentColor = MaterialTheme.colors.onBackground,
            ),
            border = BorderStroke(1.dp, difficultyColor)
        ) {
            Icon(
                painterResource(R.drawable.level),
                contentDescription = "",
                tint = difficultyColor
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = difficultyName)
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun TabRowDescription(
    coroutineScope: CoroutineScope,
    foodDetailViewModel: FoodDetailViewModel = hiltViewModel()
) {
    val foodDetailData by foodDetailViewModel.foodDetailData.collectAsState()
    val tabData2 = foodDetailViewModel.getTabTitle()
    val pagerState = rememberPagerState { tabData2.size }
    val tabIndex = pagerState.currentPage
    TabRow(modifier = Modifier
        .padding(horizontal = 16.dp)
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
        tabData2.forEachIndexed { index, text ->
            Tab(
                selectedContentColor = MaterialTheme.colors.primary,
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
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally, true)
            )
        }
    }
    HorizontalPager(
        pageSpacing = 10.dp,
        userScrollEnabled = true,
        modifier = Modifier
            .padding(horizontal = 16.dp), state = pagerState, verticalAlignment = Alignment.Top
    ) { index ->
        Text(
            text = when (index) {
                0 -> detailList.map { foodDetailData?.data?.ingredients }.joinToString { it ?: "" }
                1 -> detailList.map { foodDetailData?.data?.recipe }.joinToString { it ?: "" }
                2 -> detailList.map { foodDetailData?.data?.point }.joinToString { it ?: "" }
                else -> ""
            },
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .background(
                    Color(0xFF222228), shape = RoundedCornerShape(16.dp)
                )
                .padding(10.dp)
                .fillMaxWidth()
                .heightIn(min = 250.dp, max = 250.dp),

            )
    }
}

@Composable
private fun LazyRowForMoreFood(
    navToDetail:(id:String)->Unit,
    foodDetailViewModel: FoodDetailViewModel = hiltViewModel()
) {
    val moreFoodDetailData by foodDetailViewModel.moreFood.collectAsState()

    Text(
        text = stringResource(R.string.MorefoodByThisCategory),
        style = MaterialTheme.typography.h3,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier.padding(start = 16.dp)
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 10.dp),
        content = {
            items(moreFoodDetailData?.data ?: emptyList()) {
                Column(verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .width(136.dp)
                        .height(136.dp)
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
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    if ((it.cookTime?.plus(it.readyTime ?: 0) ?: 0) > 0)
                        Text(
                        text = "${it.readyTime?.plus(it.cookTime?:0)} دقیقه",
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

