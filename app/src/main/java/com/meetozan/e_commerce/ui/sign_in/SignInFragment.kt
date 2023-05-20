package com.meetozan.e_commerce.ui.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val signInViewModel: SignInViewModel by viewModels()
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        observer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSignInToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.btnLogin.setOnClickListener {

            val etMail = binding.etSignInEmail.text.toString()
            val etPassword = binding.etSignInPassword.text.toString()

            signInViewModel.signInWithEmail(etMail, etPassword)

        }
    }

    private fun observer() {
        signInViewModel.checkCurrentUser.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.main_graph)
            } else {
                //
            }
        }
    }

}