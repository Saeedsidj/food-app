package mobin.shabanifar.foodpart

data class foodDetail(
    val material:String,
    val recipie:String,
    val moreInfo:String
)
val detailList= listOf<foodDetail>(
    foodDetail("نون و اب مواد بیشتر","نون را با اب قاطی کنید و بگذارید تا خنک شود","نیست")
)
