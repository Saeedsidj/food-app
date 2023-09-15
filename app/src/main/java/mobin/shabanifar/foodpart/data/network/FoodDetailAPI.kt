package mobin.shabanifar.foodpart.data.network

import mobin.shabanifar.foodpart.data.models.food_Detail.FoodDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodDetailAPI {

    @GET("api/v1/food/fkw7s1ze6ygoazh")
    suspend fun getFoodDetail():Response<FoodDetailResponse>

    @GET("api/v1/food/{id}")
    suspend fun getMoreFood(@Path("id") id:List<String>):Response<List<FoodDetailResponse>>

}