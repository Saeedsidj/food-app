package mobin.shabanifar.foodpart.data.models.food_Detail


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "categoryId")
    val categoryId: String,
    @Json(name = "cookTime")
    val cookTime: Int?,
    @Json(name = "count")
    val count: String?,
    @Json(name = "difficulty")
    val difficulty: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "ingredients")
    val ingredients: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "point")
    val point: String?,
    @Json(name = "readyTime")
    val readyTime: Int?,
    @Json(name = "recipe")
    val recipe: String
)