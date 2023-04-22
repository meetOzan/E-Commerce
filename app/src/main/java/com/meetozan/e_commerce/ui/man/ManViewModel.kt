package com.meetozan.e_commerce.ui.man

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
class ManViewModel @Inject constructor(
    private val retrofitService: RetrofitService,
) : ViewModel() {

    private val _manList = MutableLiveData<List<Product>>()
    val manList: LiveData<List<Product>>
        get() = _manList

    init {
        getManProduct()
    }

    private fun getManProduct() {
        retrofitService.manProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productResponse.let {
                    _manList.value = it

                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Man Product Failure:", t.message.orEmpty())
            }
        })
    }
}