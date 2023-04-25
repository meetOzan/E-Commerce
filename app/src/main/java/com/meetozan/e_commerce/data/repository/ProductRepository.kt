package com.meetozan.e_commerce.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.meetozan.e_commerce.data.model.model.Brand
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.data.model.model.User
import com.meetozan.e_commerce.data.model.response.BrandResponse
import com.meetozan.e_commerce.data.model.response.ProductResponse
import com.meetozan.e_commerce.data.retrofit.RetrofitService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val retrofitService: RetrofitService,
    @ApplicationContext val appContext : Context
) {

    suspend fun addToFavorites(product: Product, hashMap: HashMap<Any, Any>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("favorites")
            .document(product.productName)
            .set(hashMap).await()

    suspend fun deleteFromFavorites(product: Product) = firebaseFirestore.collection("users")
        .document(firebaseAuth.currentUser?.email.toString())
        .collection("favorites")
        .document(product.productName)
        .delete().await()

    suspend fun updateUser(hashMap: HashMap<String, Any>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .update(hashMap).await()

    fun getCosmetic(list: MutableLiveData<List<Product>>) =
        retrofitService.cosmeticProducts().enqueue(object :
            Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productResponse.let {
                    list.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Cosmetic ViewModel Error: ", t.message.orEmpty())
            }
        })

    fun getElectronic(list: MutableLiveData<List<Product>>) =
        retrofitService.electronicProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productResponse.let {
                    list.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Electronic ViewModel Error: ", t.message.orEmpty())
            }
        })

    fun getFashion(list: MutableLiveData<List<Product>>) =
        retrofitService.fashionProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productResponse.let {
                    list.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Fashion ViewModel Error: ", t.message.orEmpty())
            }
        })

    fun getBrands(list: MutableLiveData<List<Brand>>) =
        retrofitService.allBrands().enqueue(object : Callback<BrandResponse> {
            override fun onResponse(call: Call<BrandResponse>, response: Response<BrandResponse>) {
                response.body()?.brandResponse.let {
                    list.value = it
                }
            }

            override fun onFailure(call: Call<BrandResponse>, t: Throwable) {
                Log.e("Brand Failure:", t.message.orEmpty())
            }
        })

    fun getHousehold(list: MutableLiveData<List<Product>>) = retrofitService.householdProducts().enqueue(object : Callback<ProductResponse> {
        override fun onResponse(
            call: Call<ProductResponse>,
            response: Response<ProductResponse>
        ) {
            response.body()?.productResponse.let {
                list.value = it
            }
        }
        override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
            Log.e("Household ViewModel Error: ",t.message.orEmpty())
        }
    })

    fun getMan(list: MutableLiveData<List<Product>>) = retrofitService.manProducts().enqueue(object : Callback<ProductResponse> {
        override fun onResponse(
            call: Call<ProductResponse>,
            response: Response<ProductResponse>
        ) {
            response.body()?.productResponse.let {
                list.value = it

            }
        }
        override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
            Log.e("Man Product Failure:", t.message.orEmpty())
        }
    })

    fun getNewest(list: MutableLiveData<List<Product>>) = retrofitService.allProducts().enqueue(object : Callback<ProductResponse> {
        override fun onResponse(
            call: Call<ProductResponse>,
            response: Response<ProductResponse>
        ) {
            response.body()?.productResponse.let {
                list.value = it
            }
        }
        override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
            Log.e("Product Failure:", t.message.orEmpty())
        }
    })

    fun getWoman(list: MutableLiveData<List<Product>>) = retrofitService.womanProducts().enqueue(object : Callback<ProductResponse>{
        override fun onResponse(
            call: Call<ProductResponse>,
            response: Response<ProductResponse>
        ) {
            response.body()?.productResponse.let {
                list.value = it
            }
        }
        override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
            Log.e("Woman Product",t.message.orEmpty())
        }
    })

    fun getFavorites(list: MutableLiveData<List<Product>>,product: MutableLiveData<Product>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("favorites")
            .addSnapshotListener { querySnapshot, firestoreException ->
                firestoreException?.let {
                    Toast.makeText(appContext, it.message, Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                querySnapshot?.let {
                    val favoritesList: ArrayList<Product> = ArrayList()
                    for (document in it) {
                        val favorites = document.toObject<Product>()
                        favoritesList.add(favorites)
                        product.postValue(favorites)
                        list.postValue(favoritesList)
                    }
                }
            }

    fun getUser(_user: MutableLiveData<User>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                if(it.exists()){
                    val user = it.toObject<User>()!!
                    _user.postValue(user)
                }else{
                    Log.e("Get User Error: ","User doesn't exist")
                }
            }
            .addOnFailureListener {
                Log.e("Get User Exception: ", it.message.orEmpty())
            }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun searchProducts(productName : String, list: MutableLiveData<List<Product>>) =
        retrofitService.searchProducts(productName).enqueue(object : Callback<ProductResponse>{
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productResponse.let {
                    if (it != null) {
                        list.postValue(it)
                    }
                    else{
                        Toast.makeText(appContext,"Sorry, we couldn't find the product you were looking for",Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Search Product Error: ", t.message.orEmpty())
            }
        })

}