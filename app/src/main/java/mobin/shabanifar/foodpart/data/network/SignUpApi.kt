package mobin.shabanifar.foodpart.data.network

import mobin.shabanifar.foodpart.data.models.sign_up.RegisterBody
import mobin.shabanifar.foodpart.data.models.sign_up.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {
    @POST("api/v1/user/register")
    suspend fun postUserSignUp(@Body registerBody: RegisterBody): Response<SignUpResponse>
}