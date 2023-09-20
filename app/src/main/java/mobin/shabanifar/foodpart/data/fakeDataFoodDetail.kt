package mobin.shabanifar.foodpart.data

import android.graphics.Point

data class foodDetail(
    val material: String, val recipie: String, val moreInfo: String
)

val detailList = listOf<foodDetail>(
    foodDetail("نون و اب مواد بیشتر", "نون را با اب قاطی کنید و بگذارید تا خنک شود", "نیست")
)

val tabData = listOf(
    "مواد اولیه", "طرز تهیه","اطلاعات بیشتر"
)