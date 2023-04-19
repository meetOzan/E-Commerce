package com.meetozan.e_commerce.ui.woman

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
class WomanViewModel @Inject constructor(
    private var retrofitService: RetrofitService
) : ViewModel() {

    private val _womanList = MutableLiveData<List<Product>>()
    val womanList : LiveData<List<Product>>
        get() = _womanList

    init {
        getWomanProducts()
    }

    private fun getWomanProducts(){
        retrofitService.womanProducts().enqueue(object : Callback<ProductResponse>{

            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productResponse.let {
                    _womanList.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Woman Product",t.message.orEmpty())
            }

        })


    }

}