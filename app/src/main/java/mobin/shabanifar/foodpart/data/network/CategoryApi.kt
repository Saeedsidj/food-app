package mobin.shabanifar.foodpart.data.network

import mobin.shabanifar.foodpart.data.models.category_response.CategoryResponse
import mobin.shabanifar.foodpart.data.models.food_response.FoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApi {
    @GET("api/v1/category")
    suspend fun getCategory(): Response<CategoryResponse>

    @GET("api/v1/food")
    suspend fun getFood(
        @Query("category") subOrCategoryId: String
    ): Response<FoodResponse>
}