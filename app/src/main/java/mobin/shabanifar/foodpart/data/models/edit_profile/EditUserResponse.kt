package mobin.shabanifar.foodpart.data.models.edit_profile


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EditUserResponse(
    @Json(name = "additionalInfo")
    val additionalInfo: AdditionalInfo,
    @Json(name = "data")
    val data: String // success
) {
    @JsonClass(generateAdapter = true)
    data class AdditionalInfo(
        @Json(name = "token")
        val token: String // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb2xsZWN0aW9uSWQiOiJfcGJfdXNlcnNfYXV0aF8iLCJleHAiOjE2OTYyNzU0MzcsImlkIjoiMHRodTVsbGtjazF6eHZnIiwidHlwZSI6ImF1dGhSZWNvcmQifQ.6NSfkLg284X5HZqJIFNvurUZDAuOPcqRh1tT9YQqmK8
    )
}