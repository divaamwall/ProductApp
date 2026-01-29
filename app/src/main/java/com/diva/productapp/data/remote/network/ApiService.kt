package com.diva.productapp.data.remote.network

import com.diva.productapp.data.remote.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getList(
        @Query("limit") limit: Int = 10,
        @Query("skip") skip: Int = 0
    ): ProductResponse
}