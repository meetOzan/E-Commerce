package com.meetozan.e_commerce.ui.newest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.data.model.response.ProductResponse
import com.meetozan.e_commerce.data.resources.remote.RetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewestViewModel @Inject constructor (
    private val retrofitService: RetrofitService
) : ViewModel(){

    private val _productList = MutableLiveData<List<Product>>()
    val productList : LiveData<List<Product>>
        get() = _productList

    init {
        getNewest()
    }

    private fun getNewest() {
        retrofitService.allProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productResponse.let {
                    _productList.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Product Failure:", t.message.orEmpty())
            }
        })
    }
}