package com.meetozan.e_commerce.ui.fashion

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meetozan.e_commerce.data.model.Product
import com.meetozan.e_commerce.data.response.ProductResponse
import com.meetozan.e_commerce.data.retrofit.RetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FashionViewModel @Inject constructor(
    private val retrofitService: RetrofitService
) :ViewModel() {

    private val _fashionList = MutableLiveData<List<Product>>()
    val fashionList : MutableLiveData<List<Product>>
    get() = _fashionList

    init {
        fashionProducts()
    }

    private fun fashionProducts() {
        retrofitService.fashionProducts().enqueue(object : Callback<ProductResponse>{
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productResponse.let {
                    _fashionList.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Fashion ViewModel Error: ",t.message.orEmpty())
            }
        })
    }
}