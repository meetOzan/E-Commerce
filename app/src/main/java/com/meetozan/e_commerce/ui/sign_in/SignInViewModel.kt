package com.meetozan.e_commerce.ui.sign_in

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
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
class SignInViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
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

    fun signInWithEmail(email: String, password: String) {
        CoroutineScope(ioDispatcher).launch {
            try {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            Toast.makeText(appContext, "Successful Login", Toast.LENGTH_SHORT)
                                .show()
                            checkLogged()
                        }
                        .addOnFailureListener {
                            Toast.makeText(appContext, it.message.orEmpty(), Toast.LENGTH_LONG)
                                .show()
                        }.await()
                } else {
                    Toast.makeText(
                        appContext,
                        "Please fill your e-mail or password",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            } catch (e: java.lang.Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(appContext, e.message.orEmpty(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}