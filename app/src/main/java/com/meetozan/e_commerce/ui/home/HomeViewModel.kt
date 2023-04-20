package com.meetozan.e_commerce.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meetozan.e_commerce.data.model.model.Brand
import com.meetozan.e_commerce.data.model.response.BrandResponse
import com.meetozan.e_commerce.data.resources.remote.RetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val retrofitService: RetrofitService
) : ViewModel() {

    private var _brandList = MutableLiveData<List<Brand>>()
    val brandList: LiveData<List<Brand>>
        get() = _brandList

    init {
        getBrands()
    }

    private fun getBrands() {
        retrofitService.allBrands().enqueue(object : Callback<BrandResponse> {

            override fun onResponse(call: Call<BrandResponse>, response: Response<BrandResponse>) {
                response.body()?.brandResponse.let {
                    _brandList.value = it
                }
            }

            override fun onFailure(call: Call<BrandResponse>, t: Throwable) {
                Log.e("Brand Failure:", t.message.orEmpty())
            }
        })
    }
}