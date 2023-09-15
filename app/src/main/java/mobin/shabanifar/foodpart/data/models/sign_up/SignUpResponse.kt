package mobin.shabanifar.foodpart.data.models.sign_up


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpResponse(
    @Json(name = "data")
    val data: Data
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "avatar")
        val avatar: String,
        @Json(name = "id")
        val id: String,
        @Json(name = "username")
        val username: String
    )
}