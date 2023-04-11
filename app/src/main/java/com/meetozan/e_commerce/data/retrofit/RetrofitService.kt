package com.meetozan.e_commerce.data.retrofit

import com.meetozan.e_commerce.data.response.BrandResponse
import com.meetozan.e_commerce.data.response.ProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

    @GET("ecommerce/all_products.php")
    fun allProducts() : Call<ProductResponse>

    @GET("ecommerce/all_brands.php")
    fun allBrands() : Call<BrandResponse>

}