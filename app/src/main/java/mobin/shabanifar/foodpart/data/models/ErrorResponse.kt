package mobin.shabanifar.foodpart.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "code")
    val code: Int, // 400
    @Json(name = "data")
    val data: Data,
    @Json(name = "message")
    val message: String // Bad Request
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "code")
        val code: String, // missing_body_field
        @Json(name = "message")
        val message: String // you should change at least one of username or password or both
    )
}