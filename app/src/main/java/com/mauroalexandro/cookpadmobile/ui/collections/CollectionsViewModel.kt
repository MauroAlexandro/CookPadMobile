package com.mauroalexandro.cookpadmobile.ui.collections

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauroalexandro.cookpadmobile.models.Collections
import com.mauroalexandro.cookpadmobile.network.ApiClient
import com.mauroalexandro.cookpadmobile.network.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectionsViewModel : ViewModel() {
    private val getCollections = MutableLiveData<Resource<Collections>>()
    private var apiClient: ApiClient = ApiClient.getInstance()

    /**
     * GET Collections
     */
    fun getCollectionsFromService() {
        getCollections.postValue(Resource.loading(null))

        apiClient.getClient()?.getCollections()?.enqueue(object :
            Callback<Collections?> {
            override fun onResponse(call: Call<Collections?>?, response: Response<Collections?>) {
                val resource: Collections? = response.body()
                getCollections.postValue(Resource.success(resource))
            }

            override fun onFailure(call: Call<Collections?>, t: Throwable) {
                Log.e("ERROR", "Error: "+t.message)
                getCollections.postValue(t.message?.let { Resource.error(it,null) })
                call.cancel()
            }
        })
    }

    /**
     * GET Collection from ID
     */
    /*fun getCollections(userName: String) {
        getCollections.postValue(Resource.loading(null))

        apiClient.getClient()?.getAuthoredCodeChallenges(userName)?.enqueue(object :
            Callback<AuthoredChallenges?> {
            override fun onResponse(call: Call<AuthoredChallenges?>?, response: Response<AuthoredChallenges?>) {
                val resource: AuthoredChallenges? = response.body()
                getCollections.postValue(Resource.success(resource))
            }

            override fun onFailure(call: Call<AuthoredChallenges?>, t: Throwable) {
                Log.e("ERROR", "Error: "+t.message)
                getCollections.postValue(t.message?.let { Resource.error(it,null) })
                call.cancel()
            }
        })
    }*/

    fun getCollections(): MutableLiveData<Resource<Collections>> {
        return getCollections
    }
}