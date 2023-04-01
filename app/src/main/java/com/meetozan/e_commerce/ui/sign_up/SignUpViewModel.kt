package com.meetozan.e_commerce.ui.sign_up

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meetozan.e_commerce.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    @ApplicationContext val appContext : Context
): ViewModel() {

    private var _checkCurrentUser = MutableLiveData<Boolean>()
    val checkCurrentUser: MutableLiveData<Boolean>
        get() = _checkCurrentUser

    init {
        checkLogged()
    }

    private fun checkLogged() {
        if (firebaseAuth.currentUser != null) {
            firebaseAuth.currentUser?.reload()
            _checkCurrentUser.value = true
        } else {
            firebaseAuth.currentUser?.reload()
            _checkCurrentUser.value = false
        }
    }

    fun signUpWithEmail(email: String, password: String, user: User){
        if(email.isNotEmpty()&&password.isNotEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    firebaseFirestore.collection("users").document()
                        .set(user)
                }
        }else{
            Toast.makeText(appContext,"Please fill your e-mail or password",Toast.LENGTH_LONG).show()
        }
    }
}