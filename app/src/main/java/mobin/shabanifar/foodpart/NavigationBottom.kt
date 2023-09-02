package mobin.shabanifar.foodpart

sealed class NavigationBottom(
    val name:Int,
    val route:String,
    val icon:Int,
){
    object Profile:NavigationBottom(R.string.account,"profile/{username}/{password}",R.drawable.person)
    object Search:NavigationBottom(R.string.search,"search",R.drawable.search)
    object Cook:NavigationBottom(R.string.what_should_i_cook,"cook",R.drawable.restaurant_menu)
    object Category:NavigationBottom(R.string.grouping,"category",R.drawable.restaurant)
}
val items = listOf(
    NavigationBottom.Profile,
    NavigationBottom.Search,
    NavigationBottom.Cook,
    NavigationBottom.Category,
)
val mainRoute =items.map { it.route }