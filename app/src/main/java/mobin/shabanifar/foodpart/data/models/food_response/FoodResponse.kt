package mobin.shabanifar.foodpart.data.models.food_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodResponse(
    @Json(name = "data")
    val data: List<FoodData>
)