package mobin.shabanifar.foodpart.data.network

import mobin.shabanifar.foodpart.data.models.food_Detail.FoodDetailResponse
import mobin.shabanifar.foodpart.data.models.food_Detail.moreFoodById.MoreFoodById
import mobin.shabanifar.foodpart.data.models.food_response.FoodData
import mobin.shabanifar.foodpart.data.models.food_response.FoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodDetailAPI {

    @GET("api/v1/food/{id}")
    suspend fun getFoodDetail(
        @Path("id") foodId:String
    ):Response<FoodDetailResponse>

    @GET("api/v1/food")
    suspend fun getMoreFood(
        @Query("ids")similarFoods:String
    ):Response<MoreFoodById>
}