package com.meetozan.e_commerce.ui.sign_up

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.data.model.model.User
import com.meetozan.e_commerce.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModels()
    private lateinit var binding: FragmentSignUpBinding
    private var isFemaleClicked: Boolean = false
    private var isMaleClicked: Boolean = false
    private var gender: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        observer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvBackToSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.imageBackToSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.imageFemale.let { femaleButton ->
            femaleButton.setOnClickListener {
                if (!isFemaleClicked) {
                    isFemaleClicked = true
                    isMaleClicked = false
                    binding.imageMale.setBackgroundResource(R.color.white)
                    binding.imageMale.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        ), PorterDuff.Mode.SRC_IN
                    )
                    femaleButton.setBackgroundResource(R.drawable.pink_circle)
                    femaleButton.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        ), PorterDuff.Mode.SRC_IN
                    )
                } else {
                    isFemaleClicked = false
                    femaleButton.setBackgroundResource(R.color.white)
                    femaleButton.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        ), PorterDuff.Mode.SRC_IN
                    )
                }
            }
        }

        binding.imageMale.let { maleButton ->
            maleButton.setOnClickListener {
                if (!isMaleClicked) {
                    isMaleClicked = true
                    isFemaleClicked = false
                    binding.imageFemale.setBackgroundResource(R.color.white)
                    binding.imageFemale.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        ), PorterDuff.Mode.SRC_IN
                    )
                    maleButton.setBackgroundResource(R.drawable.blue_circle)
                    maleButton.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        ), PorterDuff.Mode.SRC_IN
                    )
                } else {
                    isMaleClicked = false
                    maleButton.setBackgroundResource(R.color.white)
                    maleButton.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        ), PorterDuff.Mode.SRC_IN
                    )
                }
            }
        }

        binding.btnRegister.setOnClickListener {

            val etName = binding.etSignUpName.text.toString()
            val etMail = binding.etSignUpEmail.text.toString()
            val etNumber = binding.etSignUpNumber.text.toString()
            val etPassword = binding.etSignUpPassword.text.toString()

            if (isFemaleClicked == true) {
                gender = "Female"
            } else if (isMaleClicked == true) {
                gender = "Male"
            } else {
                Toast.makeText(requireContext(), "Please mark your gender.", Toast.LENGTH_SHORT)
                    .show()
            }

            val user = User(etMail, etName, etPassword, etNumber, gender)

            signUpViewModel.signUpWithEmail(etMail, etPassword, user)

        }
    }

    private fun observer() {
        signUpViewModel.checkCurrentUser.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.action_signUpFragment_to_main_graph)
            } else {
                //
            }
        }
    }
}