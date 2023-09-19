package mobin.shabanifar.foodpart.data.models.edit_profile


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EditUserBody(
    @Json(name = "newpassword")
    val newpassword: String, // 123456Sss
    @Json(name = "oldPassword")
    val oldPassword: String, // 123456Ss
    @Json(name = "username")
    val username: String // mobin
)