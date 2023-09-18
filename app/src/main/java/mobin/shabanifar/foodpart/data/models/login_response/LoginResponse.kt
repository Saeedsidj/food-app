package mobin.shabanifar.foodpart.data.models.login_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "data")
    val data: Data
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "token")
        val token: String, // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb2xsZWN0aW9uSWQiOiJfcGJfdXNlcnNfYXV0aF8iLCJleHAiOjE2OTYwMDI3MTQsImlkIjoiMXluOW9zZDAzNDZtbzR3IiwidHlwZSI6ImF1dGhSZWNvcmQifQ.gyfx07S4GHWqwLs9C3qC0CxCi1LMdKI0sQhxwiEG6yY
        @Json(name = "user")
        val user: User
    ) {
        @JsonClass(generateAdapter = true)
        data class User(
            @Json(name = "avatar")
            val avatar: String, // mohsen_j_8XcXnEezA3.svg
            @Json(name = "id")
            val id: String, // 1yn9osd0346mo4w
            @Json(name = "username")
            val username: String // mohsen.j
        )
    }
}