package mobin.shabanifar.foodpart.data.models.food_Detail


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdditionalInfo(
    @Json(name = "difficulty")
    val difficulty: Difficulty,
    @Json(name = "meals")
    val meals: List<Meal>?,
    @Json(name = "similarFoods")
    val similarFoods: List<String>?
)