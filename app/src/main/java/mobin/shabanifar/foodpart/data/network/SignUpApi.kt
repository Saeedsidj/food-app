package mobin.shabanifar.foodpart.data.network

import mobin.shabanifar.foodpart.data.models.sign_up.SignUpBody
import mobin.shabanifar.foodpart.data.models.sign_up.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignUpApi {
    @POST("api/v1/user/register")
    suspend fun postUserSignUp(@Body signUpBody: SignUpBody): Response<SignUpResponse>
}