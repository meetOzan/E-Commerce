package com.meetozan.e_commerce.ui.profile

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        binding.imageCurrentOrders.playAnimation()

        binding.imageNoCurrentOrders.playAnimation()

        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
            it.findNavController().navigate(R.id.nav_graph)
        }

        binding.btnGoHome.setOnClickListener {
            it.findNavController().navigate(R.id.homeFragment)
        }

    }

    private fun observer() {
        viewModel.user.observe(viewLifecycleOwner) { user ->

            binding.tvProfileName.text = user.username

            binding.tvProfileGender.let { gender ->
                if (user.gender == "Male") {
                    gender.text = user.gender
                    gender.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.secondaryColor
                        )
                    )
                    binding.profileImageView.setBackgroundResource(R.drawable.blue_circle)
                    binding.profileImageView.setImageResource(R.drawable.ic_person)
                }
                if (user.gender == "Female") {
                    gender.text = user.gender
                    gender.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
                    binding.profileImageView.setBackgroundResource(R.drawable.pink_circle)
                    binding.profileImageView.setImageResource(R.drawable.ic_person_woman)
                }
            }

            binding.buttonProfileEditProfile.setOnClickListener {

                val dialog =
                    LayoutInflater.from(context).inflate(R.layout.dialog_edit_profile, null)
                val builder = AlertDialog.Builder(context).setView(dialog).create()
                builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                builder.setCancelable(true)

                val etName = dialog.findViewById<TextInputLayout>(R.id.etEditName)
                etName.editText?.setText(user.username)

                val etNumber = dialog.findViewById<TextInputLayout>(R.id.etEditNumber)
                etNumber.editText?.setText(user.number)

                dialog.findViewById<ImageView>(R.id.btnCloseDialog).setOnClickListener {
                    builder.dismiss()
                }

                dialog.findViewById<Button>(R.id.btnEditProfile).setOnClickListener {
                    if (etName.isNotEmpty() && etNumber.isNotEmpty()) {

                        val updatedUser = hashMapOf<String,Any>(
                            "email" to user.email,
                            "username" to etName.editText?.text.toString(),
                            "password" to user.password,
                            "number" to etNumber.editText?.text.toString(),
                            "gender" to user.gender
                        )

                        viewModel.updateUser(updatedUser)

                        builder.dismiss()
                        Toast.makeText(
                            requireContext(), "Updated!!!", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "You have to fill all blanks",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                builder.show()
            }
        }
    }
}