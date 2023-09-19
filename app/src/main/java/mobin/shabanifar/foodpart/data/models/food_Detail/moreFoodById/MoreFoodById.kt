package mobin.shabanifar.foodpart.data.models.food_Detail.moreFoodById


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoreFoodById(
    @Json(name = "data")
    val data: List<Data>
)