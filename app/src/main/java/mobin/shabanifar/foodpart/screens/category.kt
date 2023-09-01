package mobin.shabanifar.foodpart.screens

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import mobin.shabanifar.foodpart.NavigationBottom

@Composable
fun Category() {
    Text(text = NavigationBottom.Category.name.toString(),color=Color.Red)
}