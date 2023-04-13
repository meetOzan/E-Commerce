package com.meetozan.e_commerce.ui.electronic

import android.util.Log
import androidx.lifecycle.LiveData
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
class ElectronicViewModel @Inject constructor(
    private val retrofitService: RetrofitService
) : ViewModel() {

    private val _electronicList = MutableLiveData<List<Product>>()
    val electronicList : LiveData<List<Product>>
    get() = _electronicList

    init {
        getElectronicProducts()
    }

    private fun getElectronicProducts(){

            retrofitService.electronicProducts().enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    response.body()?.productResponse.let {
                        _electronicList.value = it
                    }
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    Log.e("Electronic ViewModel Error: ",t.message.orEmpty())
                }
            })
        }
}