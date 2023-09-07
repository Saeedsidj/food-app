package mobin.shabanifar.foodpart.screens.foodDetail

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.tabData
import mobin.shabanifar.foodpart.ui.theme.FoodPartTheme
import mobin.shabanifar.foodpart.ui.theme.shapes


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun FoodDetail(
    navController: NavHostController,
    isLogin: Boolean,
    toAttributesScreen: (String) -> Unit
) {

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )
    val scaffoldState = rememberScaffoldState()
    var showMenu by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState { tabData.size }
    val tabIndex = pagerState.currentPage
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var textReportState by remember { mutableStateOf("") }
    val context = LocalContext.current
    ModalBottomSheetLayout(
        sheetBackgroundColor = MaterialTheme.colors.secondary,
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(
            bottomEnd = 0.dp,
            bottomStart = 0.dp,
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        scrimColor = Color.Transparent,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = stringResource(R.string.reportTitle),
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.onBackground
                )
                OutlinedTextField(
                    value = textReportState,
                    onValueChange = { newString -> textReportState = newString },
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .background(MaterialTheme.colors.surface, MaterialTheme.shapes.medium)
                        .fillMaxWidth(1f),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                    ),
                    shape = shapes.medium,
                    placeholder = { Text(text = "ایتجا بنویسید . . .") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        textColor = MaterialTheme.colors.onSurface,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        coroutineScope.launch {
                            textReportState = ""
                            modalSheetState.hide()
                            Toast.makeText(context, "گزارش شما ثبت شد", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    enabled = textReportState.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary,
                        disabledBackgroundColor = MaterialTheme.colors.surface,
                        disabledContentColor = MaterialTheme.colors.onSurface
                    )
                ) {
                    Text(
                        text = "ثبت",
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    ) {
        FoodPartTheme(
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = {
                    CustomSnackbarHost(
                        snackbarHostState = snackbarHostState,
                        snackbarBackgroundColor = MaterialTheme.colors.surface,
                        actionColor = MaterialTheme.colors.primary
                    )
                },
                topBar = {
                    TopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.onBackground,
                        elevation = 0.dp
                    ) {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                painterResource(R.drawable.ic_back),
                                contentDescription = "",
                                tint = MaterialTheme.colors.onBackground,
                            )
                        }
                        Text(
                            text = stringResource(R.string.food_info),
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.onBackground
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { showMenu = !showMenu }) {
                            Icon(
                                painterResource(R.drawable.more),
                                contentDescription = "",
                                tint = MaterialTheme.colors.onBackground
                            )
                            DropDownMenu(
                                showMenu,
                                isLogin,
                                coroutineScope,
                                modalSheetState,
                                snackbarHostState
                            ) {
                                showMenu = it
                            }
                        }
                    }
                }
            ) {
                MainScreen(
                    toAttributesScreen = toAttributesScreen,
                    it,
                    scrollState,
                    navController,
                    tabIndex,
                    pagerState,
                    tabData,
                    coroutineScope
                )
            }

        }
    }
}
