package com.mauroalexandro.cookpadmobile.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Mauro_Chegancas
 */
class ApiClient {
    private var retrofit: Retrofit? = null

    companion object {
        private var INSTANCE: ApiClient? = null
        fun getInstance(): ApiClient {
            if (INSTANCE == null)
                INSTANCE = ApiClient()

            return INSTANCE as ApiClient
        }
    }

    fun getClient(): ApiEndpointInterface? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://cookpad.github.io/global-mobile-hiring/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(ApiEndpointInterface::class.java)
    }
}