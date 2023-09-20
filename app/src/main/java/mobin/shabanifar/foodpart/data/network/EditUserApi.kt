package mobin.shabanifar.foodpart.data.network

import mobin.shabanifar.foodpart.data.models.edit_profile.EditUserBody
import mobin.shabanifar.foodpart.data.models.edit_profile.EditUserResponse
import mobin.shabanifar.foodpart.data.models.login_response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EditUserApi {
    @POST("api/v1/user/edit")
    suspend fun postEditUser(@Body editUserBody: EditUserBody): Response<EditUserResponse>
}