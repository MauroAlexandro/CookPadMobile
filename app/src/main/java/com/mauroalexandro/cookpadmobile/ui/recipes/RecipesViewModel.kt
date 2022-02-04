package com.mauroalexandro.cookpadmobile.ui.recipes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauroalexandro.cookpadmobile.models.Recipes
import com.mauroalexandro.cookpadmobile.network.ApiClient
import com.mauroalexandro.cookpadmobile.network.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipesViewModel : ViewModel() {
    private val getRecipes = MutableLiveData<Resource<Recipes>>()
    private var apiClient: ApiClient = ApiClient.getInstance()
    private var job: Job? = null

    /**
     * GET Recipes
     */
    fun getRecipesFromService() {
        job = CoroutineScope(Dispatchers.IO).launch {
            getRecipes.postValue(Resource.loading(null))

            apiClient.getClient()?.getRecipes()?.enqueue(object :
                Callback<Recipes?> {
                override fun onResponse(call: Call<Recipes?>?, response: Response<Recipes?>) {
                    val resource: Recipes? = response.body()
                    getRecipes.postValue(Resource.success(resource))
                }

                override fun onFailure(call: Call<Recipes?>, t: Throwable) {
                    Log.e("ERROR", "Error: " + t.message)
                    getRecipes.postValue(t.message?.let { Resource.error(it, null) })
                    call.cancel()
                }
            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun getRecipes(): MutableLiveData<Resource<Recipes>> {
        return getRecipes
    }
}