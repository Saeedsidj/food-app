package mobin.shabanifar.foodpart.data.models.food_Detail


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodDetailResponse(
    @Json(name = "additionalInfo")
    val additionalInfo: AdditionalInfo,
    @Json(name = "data")
    val data: Data
)