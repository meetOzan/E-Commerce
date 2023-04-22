package com.meetozan.e_commerce.ui.cosmetic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.data.model.response.ProductResponse
import com.meetozan.e_commerce.data.retrofit.RetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CosmeticViewModel @Inject constructor(
    private val retrofitService: RetrofitService
) : ViewModel() {

    private val _cosmeticList = MutableLiveData<List<Product>>()
    val cosmeticList : LiveData<List<Product>>
        get() = _cosmeticList

    init {
        getCosmeticProducts()
    }

    private fun getCosmeticProducts(){

        retrofitService.cosmeticProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productResponse.let {
                    _cosmeticList.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Cosmetic ViewModel Error: ",t.message.orEmpty())
            }
        })
    }

}