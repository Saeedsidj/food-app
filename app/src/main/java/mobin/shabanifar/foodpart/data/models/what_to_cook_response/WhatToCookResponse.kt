package mobin.shabanifar.foodpart.data.models.what_to_cook_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class WhatToCookResponse(
    @Json(name = "data")
    val data : List<Data>
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "categoryId")
        val categoryId: String,
        @Json(name = "cookTime")
        val cookTime: Int,
        @Json(name = "id")
        val id: String,
        @Json(name = "image")
        val image: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "readyTime")
        val readyTime: Int
    )
}