package com.meetozan.e_commerce.domain.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.meetozan.e_commerce.domain.model.data.Address
import com.meetozan.e_commerce.data.dto.BrandDto
import com.meetozan.e_commerce.data.dto.ProductDto
import com.meetozan.e_commerce.domain.model.data.User
import com.meetozan.e_commerce.domain.model.response.BrandResponse
import com.meetozan.e_commerce.domain.model.response.ProductResponse
import com.meetozan.e_commerce.data.source.ProductService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val productService: ProductService,
    @ApplicationContext val appContext: Context
) {

    suspend fun addToFavorites(productDto: ProductDto, hashMap: HashMap<Any, Any>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("favorites")
            .document(productDto.productName)
            .set(hashMap).await()

    suspend fun deleteFromFavorites(productDto: ProductDto) = firebaseFirestore.collection("users")
        .document(firebaseAuth.currentUser?.email.toString())
        .collection("favorites")
        .document(productDto.productName)
        .delete().await()

    suspend fun updateUser(hashMap: HashMap<String, Any>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .update(hashMap).await()

    fun getCosmetic(list: MutableLiveData<List<ProductDto>>) =
        productService.cosmeticProducts().enqueue(object :
            Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productDtoResponse.let {
                    list.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Cosmetic ViewModel Error: ", t.message.orEmpty())
            }
        })

    fun getElectronic(list: MutableLiveData<List<ProductDto>>) =
        productService.electronicProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productDtoResponse.let {
                    list.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Electronic ViewModel Error: ", t.message.orEmpty())
            }
        })

    fun getFashion(list: MutableLiveData<List<ProductDto>>) =
        productService.fashionProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productDtoResponse.let {
                    list.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Fashion ViewModel Error: ", t.message.orEmpty())
            }
        })

    fun getBrands(list: MutableLiveData<List<BrandDto>>) =
        productService.allBrands().enqueue(object : Callback<BrandResponse> {
            override fun onResponse(call: Call<BrandResponse>, response: Response<BrandResponse>) {
                response.body()?.brandDtoResponse.let {
                    list.value = it
                }
            }

            override fun onFailure(call: Call<BrandResponse>, t: Throwable) {
                Log.e("Brand Failure:", t.message.orEmpty())
            }
        })

    fun getHousehold(list: MutableLiveData<List<ProductDto>>) =
        productService.householdProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productDtoResponse.let {
                    list.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Household ViewModel Error: ", t.message.orEmpty())
            }
        })

    fun getMan(list: MutableLiveData<List<ProductDto>>) =
        productService.manProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productDtoResponse.let {
                    list.value = it

                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Man Product Failure:", t.message.orEmpty())
            }
        })

    fun getNewest(list: MutableLiveData<List<ProductDto>>) =
        productService.allProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productDtoResponse.let {
                    list.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Product Failure:", t.message.orEmpty())
            }
        })

    fun getWoman(list: MutableLiveData<List<ProductDto>>) =
        productService.womanProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productDtoResponse.let {
                    list.value = it
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Woman Product", t.message.orEmpty())
            }
        })

    fun getFavorites(list: MutableLiveData<List<ProductDto>>, productDto: MutableLiveData<ProductDto>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("favorites")
            .addSnapshotListener { querySnapshot, firestoreException ->
                firestoreException?.let {
                    Toast.makeText(appContext, it.message, Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                querySnapshot?.let {
                    val favoritesList: ArrayList<ProductDto> = ArrayList()
                    for (document in it) {
                        val favorites = document.toObject<ProductDto>()
                        favoritesList.add(favorites)
                        productDto.postValue(favorites)
                        list.postValue(favoritesList)
                    }
                }
            }

    fun getUser(_user: MutableLiveData<User>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val user = it.toObject<User>()!!
                    _user.postValue(user)
                } else {
                    Log.e("Get User Error: ", "User doesn't exist")
                }
            }
            .addOnFailureListener {
                Log.e("Get User Exception: ", it.message.orEmpty())
            }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun searchProducts(productName: String, list: MutableLiveData<List<ProductDto>>) =
        productService.searchProducts(productName).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                response.body()?.productDtoResponse.let {
                    list.postValue(it)
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Search Product Error: ", t.message.orEmpty())
            }
        })

    fun updateStock(product_id: Int, product_stock: Int) =
        productService.updateProduct(product_id, product_stock)
            .enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    Log.e("Update Stock Success: ", response.body()?.success.toString())
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    Log.e("Update Stock Exception: ", t.message.orEmpty())
                }
            })

    suspend fun addToBasket(productDto: ProductDto, hashMap: HashMap<Any, Any>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("basket")
            .document(productDto.productName)
            .set(hashMap).await()

    fun getBasket(list: MutableLiveData<List<ProductDto>>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("basket")
            .addSnapshotListener { querySnapshot, firestoreException ->
                firestoreException?.let {
                    Toast.makeText(appContext, it.message, Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                querySnapshot?.let {
                    val basketList: ArrayList<ProductDto> = ArrayList()
                    for (document in it) {
                        val basket = document.toObject<ProductDto>()
                        basketList.add(basket)
                        list.postValue(basketList)
                    }
                }
            }

    suspend fun updateBasketItem(productDto: ProductDto, data: Any, path: String) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("basket")
            .document(productDto.productName)
            .update(
                hashMapOf<String, Any>(
                    path to data
                )
            ).await()

    suspend fun deleteFromBasket(product_name: String) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("basket")
            .document(product_name)
            .delete().await()

    suspend fun addToOrders(product_name: String, hashMap: HashMap<Any, Any>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("orders")
            .document(product_name)
            .set(hashMap).await()

    fun getOrders(orderList: MutableLiveData<List<ProductDto>>) = firebaseFirestore.collection("users")
        .document(firebaseAuth.currentUser?.email.toString())
        .collection("orders")
        .addSnapshotListener { querySnapshot, firestoreException ->
            firestoreException?.let {
                Toast.makeText(appContext, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val _orderList: ArrayList<ProductDto> = ArrayList()
                for (document in it) {
                    val order = document.toObject<ProductDto>()
                    _orderList.add(order)
                    orderList.postValue(_orderList)
                }
            }
        }

    suspend fun addAddress(address: HashMap<Any, Any>, addressName: String) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("address")
            .document(addressName)
            .set(address).await()

    suspend fun deleteAddress(addressName: String) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("address")
            .document(addressName)
            .delete().await()

    fun getAddress(addressName: String, selectedAddress: MutableLiveData<Address>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("address")
            .document(addressName)
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val address = it.toObject<Address>()!!
                    selectedAddress.postValue(address)
                } else {
                    Log.e("Get Address Error: ", "Address doesn't exist")
                }
            }
            .addOnFailureListener {
                Log.e("Get Address Error: ", it.message.orEmpty())
            }

    fun getAllAddress(addressList: MutableLiveData<List<Address>>) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("address")
            .addSnapshotListener { querySnapshot, firestoreException ->
                firestoreException?.let {
                    Toast.makeText(appContext, it.message, Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                querySnapshot?.let {
                    val _addressList: ArrayList<Address> = ArrayList()
                    for (document in it) {
                        val address = document.toObject<Address>()
                        _addressList.add(address)
                        addressList.postValue(_addressList)
                    }
                }
            }

    suspend fun updateAddress(addressName: String,path: String,data: Any) =
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.email.toString())
            .collection("address")
            .document(addressName)
            .update(
                hashMapOf(
                    path to data
                ) as Map<String, Any>
            ).await()
}