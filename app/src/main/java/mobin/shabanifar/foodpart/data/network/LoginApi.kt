package mobin.shabanifar.foodpart.data.network

import mobin.shabanifar.foodpart.data.models.login_response.LoginResponse
import mobin.shabanifar.foodpart.data.models.sign_up.SignUpBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("api/v1/user/login")
    suspend fun postUserLogin(@Body signUpBody: SignUpBody): Response<LoginResponse>
}