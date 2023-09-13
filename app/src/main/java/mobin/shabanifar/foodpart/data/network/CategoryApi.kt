package mobin.shabanifar.foodpart.data.network

import mobin.shabanifar.foodpart.data.models.category_response.CategoryResponse
import mobin.shabanifar.foodpart.data.models.food_response.FoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {
    @GET("api/v1/category")
    suspend fun getCategory(): Response<CategoryResponse>
    @GET("api/v1/food?category={id}")
    suspend fun getFood(
        @Path("id") subOrCategoryId: String
    ):Response<FoodResponse>
}