package com.meetozan.e_commerce.data.retrofit

import com.meetozan.e_commerce.data.model.response.BrandResponse
import com.meetozan.e_commerce.data.model.response.ProductResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {

    @GET("ecommerce/all_products.php")
    fun allProducts(): Call<ProductResponse>

    @GET("ecommerce/all_brands.php")
    fun allBrands(): Call<BrandResponse>

    @GET("ecommerce/woman_products.php")
    fun womanProducts(): Call<ProductResponse>

    @GET("ecommerce/man_products.php")
    fun manProducts(): Call<ProductResponse>

    @GET("ecommerce/fashion_products.php")
    fun fashionProducts(): Call<ProductResponse>

    @GET("ecommerce/electronic_products.php")
    fun electronicProducts(): Call<ProductResponse>

    @GET("ecommerce/household_products.php")
    fun householdProducts(): Call<ProductResponse>

    @GET("ecommerce/cosmetic_products.php")
    fun cosmeticProducts(): Call<ProductResponse>

    @POST("ecommerce/search_products.php")
    @FormUrlEncoded
    fun searchProducts(@Field("product_name") product_name: String): Call<ProductResponse>

}