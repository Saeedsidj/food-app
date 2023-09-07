package mobin.shabanifar.foodpart.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val darkColorPalette = darkColors(
    primary = primary,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface,
    secondary = secondary,
)

@Composable
fun FoodPartTheme(
    content: @Composable () -> Unit
) {
    val colors = darkColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}