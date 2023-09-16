package mobin.shabanifar.foodpart.data.network

import mobin.shabanifar.foodpart.data.models.search.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("api/v1/search")
    suspend fun getSearchedFoods(
        @Query("query") textFiledValue: String
    ): Response<SearchResponse>
}