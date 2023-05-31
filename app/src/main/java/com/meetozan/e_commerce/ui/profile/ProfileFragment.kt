package com.meetozan.e_commerce.ui.profile

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.data.dto.ProductDto
import com.meetozan.e_commerce.databinding.FragmentProfileBinding
import com.meetozan.e_commerce.ui.adapter.ProductAdapter
import com.meetozan.e_commerce.ui.adapter.ProfileAddressAdapter
import com.meetozan.e_commerce.ui.address.AddressViewModel
import com.meetozan.e_commerce.ui.favorites.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private val addressViewModel: AddressViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private lateinit var adapterCurrent: ProductAdapter
    private lateinit var addressAdapter: ProfileAddressAdapter
    private lateinit var rvOrders: RecyclerView
    private lateinit var rvAddress: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userObserver()
        orderObserver()
        addressObserver()

        binding.imageCurrentOrders.playAnimation()

        binding.imageNoCurrentOrders.playAnimation()

        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
            it.findNavController().apply {
                popBackStack(R.id.nav_graph, true)
                navigate(R.id.nav_graph)
            }
        }

        binding.btnGoHome.setOnClickListener {
            it.findNavController().navigate(R.id.homeFragment)
        }

        binding.profileTopPlaceholder.setOnClickListener {
            return@setOnClickListener
        }

        binding.cvOrderView.setOnClickListener {
            return@setOnClickListener
        }

        binding.profileImageView.setOnClickListener {
            return@setOnClickListener
        }

        (binding.btnOpenAddresses).setOnClickListener {
            if (binding.expandableLayoutAddresses.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(
                    binding.cvProfileAddresses, AutoTransition()
                )
                TransitionManager.beginDelayedTransition(
                    binding.cvCurrentOrder, AutoTransition()
                )
                TransitionManager.beginDelayedTransition(
                    binding.cvNoCurrentOrder, AutoTransition()
                )

                binding.expandableLayoutAddresses.visibility = View.VISIBLE
                binding.btnOpenAddresses.animate().apply {
                    duration = 500
                    rotationX(180f)
                }.start()
                return@setOnClickListener
            }
            if (binding.expandableLayoutAddresses.visibility == View.VISIBLE) {
                binding.expandableLayoutAddresses.visibility = View.GONE
                TransitionManager.beginDelayedTransition(
                    binding.cvProfileAddresses, AutoTransition()
                )
                TransitionManager.beginDelayedTransition(
                    binding.cvCurrentOrder, AutoTransition()
                )
                TransitionManager.beginDelayedTransition(
                    binding.cvNoCurrentOrder, AutoTransition()
                )
                binding.btnOpenAddresses.animate().apply {
                    duration = 500
                    rotationX(-0f)
                }.start()
                return@setOnClickListener
            }
        }
    }

    private fun userObserver() {
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
                    if (etName.editText?.text.toString() != "" && etNumber.editText?.text.toString() != "") {
                        val updatedUser = hashMapOf<String, Any>(
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
                        if (etName.editText?.text.toString() == "") {
                            etName.error = "Please Enter Your Name"
                        }
                        if (etNumber.editText?.text.toString() == "") {
                            etNumber.error = "Please Enter Your Number"
                        }
                        Toast.makeText(
                            requireContext(),
                            "Please Fill Blanks",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                builder.show()
            }
        }
    }

    private fun addressObserver() {
        addressViewModel.addressList.observe(viewLifecycleOwner) { addressList ->
            with(binding){
                if (addressList.isNullOrEmpty()) {
                    cvAddressesNotFound.visibility = View.VISIBLE
                    rvAddresses.visibility = View.GONE
                } else {
                    cvAddressesNotFound.visibility = View.GONE
                    rvAddresses.visibility = View.VISIBLE
                    addressAdapter = ProfileAddressAdapter(addressList,requireContext(),addressViewModel)
                    rvAddress = rvAddresses
                    rvAddress.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    rvAddress.adapter = addressAdapter
                }
            }
        }
    }

    private fun orderObserver() {
        viewModel.orderList.observe(viewLifecycleOwner) { orderList ->
            with(binding) {
                if (orderList.isNullOrEmpty()) {
                    cvNoCurrentOrder.visibility = View.VISIBLE
                    cvCurrentOrder.visibility = View.GONE
                } else {
                    cvNoCurrentOrder.visibility = View.GONE
                    cvCurrentOrder.visibility = View.VISIBLE
                    adapterCurrent = ProductAdapter(
                        orderList as MutableList<ProductDto>,
                        requireContext(),
                        layoutInflater,
                        favoritesViewModel
                    )
                    rvOrders = rvCurrentOrders
                    rvOrders.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    rvOrders.adapter = adapterCurrent
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}