package com.shajib.actualhttprequest

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * @author Shajib
 * @since Sept 02, 2024
 **/
interface ApiService {
    @GET("locations/v1/regions")
    fun getRegions(
        //@Header("apikey") apiKey: String?
        @Query("apikey") apiKey: String?
    ): Call<ResponseBody>?
}