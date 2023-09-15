package mobin.shabanifar.foodpart.data.network

import mobin.shabanifar.foodpart.data.models.category_response.CategoryResponse
import mobin.shabanifar.foodpart.data.models.what_to_cook_response.WhatToCookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WhatToCookApi {
    @GET("/api/v1/what-to-cook")
    suspend fun getWhatToCook(@QueryMap queries: Map<String, String>): Response<WhatToCookResponse>
}