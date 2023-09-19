package mobin.shabanifar.foodpart.data.models.sign_up


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterBody(
    @Json(name = "password")
    var password: String,
    @Json(name = "username")
    var username: String
)