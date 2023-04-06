package com.meetozan.e_commerce.ui.sign_up

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meetozan.e_commerce.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineContext,
    @ApplicationContext val appContext: Context
) : ViewModel() {

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

    fun signUpWithEmail(email: String, password: String, user: User) {
        CoroutineScope(ioDispatcher).launch {
            try {
                if (email.isNotEmpty() || password.isNotEmpty() || user.username.isNotEmpty() || user.number.isNotEmpty() || user.gender.isNotEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            firebaseFirestore.collection("users").document(user.username)
                                .set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(appContext, "Successful", Toast.LENGTH_LONG)
                                        .show()
                                    checkLogged()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        appContext,
                                        "A fail occurred to sign up",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.e("Firestore Sign Up Exception:", it.message.orEmpty())
                                }
                        }.await()
                } else {
                    Toast.makeText(
                        appContext,
                        "Please fill your information ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: java.lang.Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(appContext, e.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}