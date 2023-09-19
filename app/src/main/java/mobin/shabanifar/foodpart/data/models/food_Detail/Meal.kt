package mobin.shabanifar.foodpart.data.models.food_Detail


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meal(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String
)