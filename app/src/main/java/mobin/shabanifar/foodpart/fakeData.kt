package mobin.shabanifar.foodpart

// فیک دیتا آیتم های غذا
val itemImage = R.drawable.food_itemj

// کلاس فیک دیتا برای آیتم های غذا
data class FakeFoods(
    val image: Int,
    val name: String,
    val time: Int,
    var breakfast: Boolean = true,
    var lunch: Boolean = false,
    var dinner: Boolean = true
)

// کلاس فیک دیتا برای آیتم های کتگوری
data class CategoryItems(
    val name: String,
    val image: Int,
    var isClicked: Boolean = false
)

val fakeFoods = listOf<FakeFoods>(
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "کوکو", time = 15),
    FakeFoods(image = itemImage, name = "آش", time = 15),
    FakeFoods(image = itemImage, name = "آش", time = 15),
    FakeFoods(image = itemImage, name = "پلو", time = 15),
    FakeFoods(image = itemImage, name = "چلو گوشت", time = 15),
    FakeFoods(image = itemImage, name = "قیمه", time = 15),
    FakeFoods(image = itemImage, name = "قرمه سبزی", time = 15),
    FakeFoods(image = itemImage, name = "پیتزا", time = 15),
    FakeFoods(image = itemImage, name = "ماکارانی", time = 15),
    FakeFoods(image = itemImage, name = "آش دوغ", time = 15),
    FakeFoods(image = itemImage, name = "پیراشکی", time = 15),
    FakeFoods(image = itemImage, name = "فسنجون", time = 15),
    FakeFoods(image = itemImage, name = "فسنجان", time = 15),
    FakeFoods(image = itemImage, name = "کباب ترکی", time = 15),
    FakeFoods(image = itemImage, name = "تاس کباب", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "قرمه سبزی", time = 15),
    FakeFoods(image = itemImage, name = "پیتزا", time = 15),
    FakeFoods(image = itemImage, name = "ماکارانی", time = 15),
    FakeFoods(image = itemImage, name = "آش دوغ", time = 15),
    FakeFoods(image = itemImage, name = "پیراشکی", time = 15),
    FakeFoods(image = itemImage, name = "فسنجون", time = 15),
    FakeFoods(image = itemImage, name = "فسنجان", time = 15),
    FakeFoods(image = itemImage, name = "کباب ترکی", time = 15),
    FakeFoods(image = itemImage, name = "تاس کباب", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی3", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی2", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "کوکو", time = 15),
    FakeFoods(image = itemImage, name = "آش", time = 15),
    FakeFoods(image = itemImage, name = "آش", time = 15),
    FakeFoods(image = itemImage, name = "پلو", time = 15),
    FakeFoods(image = itemImage, name = "چلو گوشت", time = 15),
    FakeFoods(image = itemImage, name = "قیمه", time = 15),
    FakeFoods(image = itemImage, name = "قرمه سبزی", time = 15),
    FakeFoods(image = itemImage, name = "پیتزا", time = 15),
    FakeFoods(image = itemImage, name = "ماکارانی", time = 15),
    FakeFoods(image = itemImage, name = "آش دوغ", time = 15),
    FakeFoods(image = itemImage, name = "پیراشکی", time = 15),
    FakeFoods(image = itemImage, name = "فسنجون", time = 15),
    FakeFoods(image = itemImage, name = "فسنجان", time = 15),
    FakeFoods(image = itemImage, name = "کباب ترکی", time = 15),
    FakeFoods(image = itemImage, name = "تاس کباب", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "قرمه سبزی", time = 15),
    FakeFoods(image = itemImage, name = "پیتزا", time = 15),
    FakeFoods(image = itemImage, name = "ماکارانی", time = 15),
    FakeFoods(image = itemImage, name = "آش دوغ", time = 15),
    FakeFoods(image = itemImage, name = "پیراشکی", time = 15),
    FakeFoods(image = itemImage, name = "فسنجون", time = 15),
    FakeFoods(image = itemImage, name = "فسنجان", time = 15),
    FakeFoods(image = itemImage, name = "کباب ترکی", time = 15),
    FakeFoods(image = itemImage, name = "تاس کباب", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "سیب زمینی", time = 15),
    FakeFoods(image = itemImage, name = "asd", time = 15),
    FakeFoods(image = itemImage, name = "asd", time = 15),
    FakeFoods(image = itemImage, name = "asd", time = 15),
    FakeFoods(image = itemImage, name = "asd", time = 15),
)

// فیک دیتا کتگوری
val categoryItems = listOf<CategoryItems>(
    CategoryItems(name = "آبگوشت", image = R.drawable.abgoosht),
    CategoryItems(name = "بی ساب", image = R.drawable.abgoosht),
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
)

// لیست ساب کتگوری ها - لیست استرینگی ساده
val subCategoryList =
    listOf<String>(
        "آبگوشت",
        "همبرگر",
        "اشکنه",
        "آش رشته",
        "آبگوشت",
        "همبرگر",
        "اشکنه",
        "آش رشته",
        "آبگوشت",
        "همبرگر",
        "اشکنه",
        "آش رشته",
        "آبگوشت",
        "همبرگر",
        "اشکنه",
        "آش رشته",

        )
