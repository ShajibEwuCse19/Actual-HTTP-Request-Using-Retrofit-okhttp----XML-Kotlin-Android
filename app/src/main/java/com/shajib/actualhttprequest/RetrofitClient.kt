package com.shajib.actualhttprequest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Shajib
 * @since Sept 02, 2024
 **/
class RetrofitClient {
    private var retrofit: Retrofit? = null
    private constructor() {
        var okHttpClient = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        var baseUrl = "http://dataservice.accuweather.com/"

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        private var mInstance: RetrofitClient? = null

        fun getInstance(): RetrofitClient? {
            if(mInstance == null) {
                mInstance = RetrofitClient()
            }
            return mInstance
        }
    }

    fun getApi() : ApiService? {
        return retrofit?.create(ApiService::class.java)
    }
}