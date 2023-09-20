package mobin.shabanifar.foodpart.data.models.food_Detail

import android.accounts.AuthenticatorDescription
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReportBody(
    @Json(name = "description")
    val description:String
)
