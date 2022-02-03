package com.mauroalexandro.cookpadmobile.ui.recipes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauroalexandro.cookpadmobile.models.Recipe
import com.mauroalexandro.cookpadmobile.network.ApiClient
import com.mauroalexandro.cookpadmobile.network.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Mauro_Chegancas
 */
class RecipeDetailViewModel : ViewModel() {
    private val getRecipe = MutableLiveData<Resource<Recipe>>()
    private var apiClient: ApiClient = ApiClient.getInstance()

    /**
     * GET Recipe
     */
    fun getRecipeFromService(recipeID: Int) {
        getRecipe.postValue(Resource.loading(null))

        apiClient.getClient()?.getRecipeWithID(recipeID)?.enqueue(object :
            Callback<Recipe?> {
            override fun onResponse(call: Call<Recipe?>?, response: Response<Recipe?>) {
                val resource: Recipe? = response.body()
                getRecipe.postValue(Resource.success(resource))
            }

            override fun onFailure(call: Call<Recipe?>, t: Throwable) {
                Log.e("ERROR", "Error: "+t.message)
                getRecipe.postValue(t.message?.let { Resource.error(it,null) })
                call.cancel()
            }
        })
    }

    fun getRecipe(): MutableLiveData<Resource<Recipe>> {
        return getRecipe
    }
}