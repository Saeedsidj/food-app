package mobin.shabanifar.foodpart

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationBottom(
    val name:Int,
    val route:String,
    val icon:Int,
){
    object profile:NavigationBottom(R.string.account,"profile",R.drawable.person)
    object Search:NavigationBottom(R.string.search,"search",R.drawable.search)
    object Cook:NavigationBottom(R.string.what_should_i_cook,"cook",R.drawable.restaurant_menu)
    object Category:NavigationBottom(R.string.grouping,"category",R.drawable.restaurant)
}
val items = listOf(
    NavigationBottom.profile,
    NavigationBottom.Search,
    NavigationBottom.Cook,
    NavigationBottom.Category,
)
val mainRoute =items.map { it.route }