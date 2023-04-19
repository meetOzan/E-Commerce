package com.meetozan.e_commerce.ui.household

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meetozan.e_commerce.data.model.Product
import com.meetozan.e_commerce.data.response.ProductResponse
import com.meetozan.e_commerce.data.remote.RetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HouseholdViewModel @Inject constructor(
    private val retrofitService: RetrofitService
) : ViewModel() {



    private val _householdList = MutableLiveData<List<Product>>()
    val householdList : LiveData<List<Product>>
        get() = _householdList

    init {
        getHouseholdProducts()
    }

    private fun getHouseholdProducts(){

        retrofitService.householdProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productResponse.let {
                    _householdList.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Household ViewModel Error: ",t.message.orEmpty())
            }
        })
    }
}