package mobin.shabanifar.foodpart

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

// کلاس فیک دیتا برای آیتم های غذا
data class FakeFoods(
    val image: Int,
    val name: String,
    val time: Int,
    var breakfast: Boolean = true,
    var lunch: Boolean = false,
    var dinner: Boolean = true
)

// فیک دیتا آیتم های غذا
val itemImage = R.drawable.food_itemj
val fakeFoodItems = listOf<FakeFoods>(
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی2", time = 15),
)














// کلاس فیک دیتا برای آیتم های کتگوری
data class CategoryItems(
    val name: String,
    val image: Int,
    var isClicked: Boolean = false
)
// فیک دیتا کتگوری
val categoryListItems = listOf<CategoryItems>(
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "غذا نیست", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
        CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
    )
