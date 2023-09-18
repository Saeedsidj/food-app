package mobin.shabanifar.foodpart.data.models.search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "data")
    val data: List<SearchedFood>
)