package mobin.shabanifar.foodpart.data.network

import retrofit2.Response
import retrofit2.http.GET

interface LogoutApi {

    @GET("api/v1/user/logout")
    suspend fun getLogOutUser():Response<Unit>
}
