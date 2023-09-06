package mobin.shabanifar.foodpart.screens.foodDetail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.NavigationBottom
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.ui.theme.shapes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenu(
    showMenu: Boolean,
    isLogin: Boolean,
    coroutineScope: CoroutineScope,
    modalSheetState: ModalBottomSheetState,
    snackbarHostState: SnackbarHostState,
    showMenuFalse: (Boolean) -> Unit
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = {
            showMenuFalse(false)
        },
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.surface
            )
    ) {
        if (isLogin) {
            DropdownMenuItem(
                onClick = {
                    coroutineScope.launch {
                        showMenuFalse(false)
                        modalSheetState.show()
                    }
                }, contentPadding = PaddingValues(10.dp)
            ) {
                Icon(
                    painterResource(R.drawable.report),
                    contentDescription = ""
                )
                Text(text = "گزارش")
            }
            DropdownMenuItem(
                onClick = { }) {
                Icon(
                    painterResource(R.drawable.share),
                    contentDescription = ""
                )
                Text(text = "ارسال")
            }
            BookMark(coroutineScope, snackbarHostState)
        } else {
            BookMark(coroutineScope, snackbarHostState)
        }
    }
}

@Composable
fun BookMark(
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    DropdownMenuItem(
        onClick = {
            coroutineScope.launch {
                val snackbarResult =
                    snackbarHostState.showSnackbar(
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
            painterResource(R.drawable.bookmark),
            contentDescription = ""
        )
        Text(text = "ذخیره")
    }
}

@Composable
fun CustomSnackbarHost(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarBackgroundColor: Color = MaterialTheme.colors.surface,
    actionColor: Color = MaterialTheme.colors.primary
) {
    SnackbarHost(
        modifier = modifier.padding(5.dp),
        hostState = snackbarHostState,
        snackbar = { snackbarData ->
            Snackbar(
                modifier = Modifier.padding(8.dp),
                snackbarData = snackbarData,
                backgroundColor = snackbarBackgroundColor,
                contentColor = MaterialTheme.colors.onSurface,
                actionColor = actionColor
            )
        }
    )
}
@Composable
fun CustomSnakBarHost(snackbarHostState: SnackbarHostState) {
    CustomSnackbarHost(
        snackbarHostState = snackbarHostState,
        snackbarBackgroundColor = MaterialTheme.colors.surface,
        actionColor = MaterialTheme.colors.primary
    )
}
