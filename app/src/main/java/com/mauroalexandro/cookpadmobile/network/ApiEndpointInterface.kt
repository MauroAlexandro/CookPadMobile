package com.mauroalexandro.cookpadmobile.network

import com.mauroalexandro.cookpadmobile.models.Collection
import com.mauroalexandro.cookpadmobile.models.Collections
import com.mauroalexandro.cookpadmobile.models.Recipes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Mauro_Chegancas
 */
interface ApiEndpointInterface {
    @GET("collections/")
    fun getCollections(): Call<Collections>

    @GET("collections/{id}")
    fun getCollectionWithID(@Path(value = "id", encoded = true) id: String): Call<Collection>

    @GET("collections/{id}/recipes")
    fun getRecipesFromCollection(@Path(value = "id", encoded = true) id: String): Call<Recipes>

    @GET("recipes/")
    fun getRecipes(): Call<Recipes>

    @GET("recipes/{id}")
    fun getRecipeWithID(@Path(value = "id", encoded = true) id: String): Call<Recipes>
}