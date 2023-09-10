package mobin.shabanifar.foodpart.utils

import mobin.shabanifar.foodpart.R

sealed class NavigationBottom(
    val name: Int,
    val route: String,
    val icon: Int?,
) {
    object Profile :
        NavigationBottom(R.string.account, "profile/{username}/{password}", R.drawable.person)

    object Search : NavigationBottom(R.string.search, "search", R.drawable.search)
    object Cook : NavigationBottom(R.string.what_should_i_cook, "cook", R.drawable.restaurant_menu)
    object Category : NavigationBottom(R.string.grouping, "category", R.drawable.restaurant)
    object FoodDetail :
        NavigationBottom(R.string.food_info, "foodDetail/{degree}/{name}/{time}/{image}", null)

    object FoodPhoto : NavigationBottom(R.string.photo, "photo", null)
    object WhatToCook : NavigationBottom(
        R.string.what_should_i_cook,
        "whatToCookList?whatDoYouHave={whatDoYouHave}&howMuchTimeHave={howMuchTimeHave}&level={level}",
        null
    )

    object SignUp : NavigationBottom(R.string.register, "sign_up", null)
    object Login : NavigationBottom(R.string.login, "login", null)
    object ShowFoodByAttributes :
        NavigationBottom(R.string.show_food_by_attribute, "showFoodByAttributes/{title}", null)

}

val items = listOf(
    NavigationBottom.Category,
    NavigationBottom.Cook,
    NavigationBottom.Search,
    NavigationBottom.Profile,
)
val mainRoute = items.map { it.route }