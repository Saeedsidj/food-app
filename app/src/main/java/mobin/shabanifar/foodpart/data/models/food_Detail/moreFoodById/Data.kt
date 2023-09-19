package mobin.shabanifar.foodpart.data.models.food_Detail.moreFoodById


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "categoryId")
    val categoryId: String,
    @Json(name = "cookTime")
    val cookTime: Int?,
    @Json(name = "id")
    val id: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "readyTime")
    val readyTime: Int?
)