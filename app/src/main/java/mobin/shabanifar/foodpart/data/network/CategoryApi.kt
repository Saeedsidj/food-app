package mobin.shabanifar.foodpart.data.network

import mobin.shabanifar.foodpart.data.models.category_response.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApi {
    @GET("api/v1/category")
    suspend fun getCategory(): Response<List<CategoryResponse>>
}