package mobin.shabanifar.foodpart.ui.screens.foodDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.viewmodel.FoodDetailViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenu(
    showMenu: Boolean,
    isLoggedIn: String?,
    coroutineScope: CoroutineScope,
    modalSheetState: ModalBottomSheetState,
    snackbarHostState: SnackbarHostState,
    foodDetailViewModel: FoodDetailViewModel,
    showMenuFalse: (Boolean) -> Unit,
) {
    DropdownMenu(
        expanded = showMenu, onDismissRequest = {
            showMenuFalse(false)
        }, modifier = Modifier
            .background(
                color = MaterialTheme.colors.surface
            )
    ) {
        if (!isLoggedIn.isNullOrEmpty()) {
            DropdownMenuItem(
                onClick = {
                    coroutineScope.launch {
                        showMenuFalse(false)
                        modalSheetState.show()
                    }
                }, contentPadding = PaddingValues(10.dp)
            ) {
                Icon(
                    painterResource(R.drawable.report), contentDescription = ""
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = stringResource(R.string.report))
            }
            DropdownMenuItem(onClick = { }) {
                Icon(
                    painterResource(R.drawable.share), contentDescription = ""
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = stringResource(R.string.send))
            }
            DropdownMenuItem(
                onClick = {
                    coroutineScope.launch {
                        foodDetailViewModel.addToBookMark(foodDetailViewModel.foodId,isLoggedIn)
                        val snackbarResult = snackbarHostState.showSnackbar(
                            message = "دستور به علاقه مندی ها اضافه شد",
                            actionLabel = "ذخیره شده ها",
                        )
                        when (snackbarResult) {
                            SnackbarResult.ActionPerformed -> TODO()
                            else -> SnackbarResult.Dismissed
                        }
                    }
                },
            ) {
                Icon(
                    painterResource(R.drawable.bookmark), contentDescription = ""
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = stringResource(R.string.save))
            }
        } else {
            DropdownMenuItem(onClick = { }) {
                Icon(
                    painterResource(R.drawable.share), contentDescription = ""
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = stringResource(R.string.send))
            }
        }
    }
}
@Composable
fun CustomSnackbarHost(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarBackgroundColor: Color = MaterialTheme.colors.secondary,
    actionColor: Color = MaterialTheme.colors.primary
) {
    SnackbarHost(modifier = modifier.padding(5.dp),
        hostState = snackbarHostState,
        snackbar = { snackbarData ->
            Snackbar(
                modifier = Modifier.padding(8.dp),
                snackbarData = snackbarData,
                backgroundColor = snackbarBackgroundColor,
                contentColor = MaterialTheme.colors.onSurface,
                actionColor = actionColor
            )
        })
}