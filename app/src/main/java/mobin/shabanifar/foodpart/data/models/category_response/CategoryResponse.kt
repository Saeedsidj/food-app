package mobin.shabanifar.foodpart.data.models.category_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryResponse(
    @Json(name = "data")
    val data: List<CategoryData>
)

