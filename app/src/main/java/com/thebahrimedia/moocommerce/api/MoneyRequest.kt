package com.thebahrimedia.moocommerce.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoneyRequest {
    @GET("/latest")
    fun getRates(@Query("from") base: String): Call<MoneyPayload>
}