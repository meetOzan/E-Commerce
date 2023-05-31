package com.meetozan.e_commerce.ui.address

import android.app.AlertDialog
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.databinding.FragmentAddressBinding
import com.meetozan.e_commerce.ui.adapter.AddressAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressFragment : Fragment() {

    private var _binding: FragmentAddressBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddressViewModel by viewModels()
    private lateinit var adapter: AddressAdapter
    private lateinit var rv: RecyclerView

    override fun onResume() {
        super.onResume()

        val cities = resources.getStringArray(R.array.cities)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.city_dropdown_item, cities)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        binding.cvAddressesLayout.background = null

        binding.cvAddresses.setOnClickListener {
            if (binding.rvSelectAddress.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(
                    binding.cvAddresses, AutoTransition()
                )

                binding.rvSelectAddress.visibility = View.GONE

                TransitionManager.beginDelayedTransition(
                    binding.addressScrollView, AutoTransition()
                )
                return@setOnClickListener
            }
            if (binding.rvSelectAddress.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(
                    binding.cvAddresses, AutoTransition()
                )
                TransitionManager.beginDelayedTransition(
                    binding.addressScrollView, AutoTransition()
                )
                binding.rvSelectAddress.visibility = View.VISIBLE

                return@setOnClickListener
            }
        }

        binding.btnNextToPayment.setOnClickListener {

            if (binding.etAddressName.editText?.text.toString().isNotEmpty() &&
                binding.etCity.editText?.text.toString().isNotEmpty() &&
                binding.etDistrict.editText?.text.toString().isNotEmpty() &&
                binding.etStreet.editText?.text.toString().isNotEmpty() &&
                binding.etApartment.editText?.text.toString().isNotEmpty() &&
                binding.etFlatNo.editText?.text.toString().isNotEmpty()
            ) {

                val addressName = binding.etAddressName.editText?.text.toString()
                val cityName = binding.etCity.editText?.text.toString()
                val districtName = binding.etDistrict.editText?.text.toString()
                val neighbourhoodName = binding.etNeighbourhood.editText?.text.toString()
                val streetName = binding.etStreet.editText?.text.toString()
                val apartmentName = binding.etApartment.editText?.text.toString()
                val flatNo = binding.etFlatNo.editText?.text.toString()
                val isDefault = binding.checkBoxAddressDefaultAddress.isChecked

                val addressHashMap = hashMapOf<Any, Any>(
                    "name" to addressName,
                    "city" to cityName,
                    "district" to districtName,
                    "neighbourhood" to neighbourhoodName,
                    "street" to streetName,
                    "apartment" to apartmentName,
                    "no" to flatNo,
                    "isDefault" to isDefault
                )

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Are you wanted to save address ?")
                builder.setMessage("Tap OK button for save address")

                builder.setPositiveButton(android.R.string.ok) { _, _ ->
                    viewModel.addAddress(addressHashMap, addressName)
                    if(isDefault){
                        viewModel.addressList.observe(viewLifecycleOwner){
                            for (index in it.indices){
                                viewModel.updateAddress(it[index].name,"isDefault",false)
                            }
                        }
                    }
                    it.findNavController().navigate(R.id.action_addressFragment_to_paymentFragment)
                }

                builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                    it.findNavController().navigate(R.id.action_addressFragment_to_paymentFragment)
                }

                builder.show()

            } else {

                with(binding.etAddressOwnerName.editText?.text) {
                    if (this?.isEmpty()!!) {
                        binding.etAddressOwnerName.error = "Please Enter Name"
                    } else {
                        binding.etAddressOwnerName.error = null
                    }
                }
                with(binding.etAddressOwnerPhoneNumber.editText?.text) {
                    if (this?.isEmpty()!!) {
                        binding.etAddressOwnerPhoneNumber.error = "Please Enter Phone Number"
                    } else {
                        binding.etAddressOwnerPhoneNumber.error = null
                    }
                }
                with(binding.etAddressName.editText?.text) {
                    if (this?.isEmpty()!!) {
                        binding.etAddressName.error = "Please Enter Address Name"
                    } else {
                        binding.etAddressName.error = null
                    }
                }
                with(binding.etCity.editText?.text) {
                    if (this?.isEmpty()!!) {
                        binding.etCity.error = "Please Enter City"
                    } else {
                        binding.etCity.error = null
                    }
                }
                with(binding.etDistrict.editText?.text) {
                    if (this?.isEmpty()!!) {
                        binding.etDistrict.error = "Please Enter District"
                    } else {
                        binding.etDistrict.error = null
                    }
                }
                with(binding.etNeighbourhood.editText?.text) {
                    if (this?.isEmpty()!!) {
                        binding.etNeighbourhood.error = "Please Enter Neighbourhood"
                    } else {
                        binding.etNeighbourhood.error = null
                    }
                }
                with(binding.etStreet.editText?.text) {
                    if (this?.isEmpty()!!) {
                        binding.etStreet.error = "Please Enter Street"
                    } else {
                        binding.etStreet.error = null
                    }
                }
                with(binding.etApartment.editText?.text) {
                    if (this?.isEmpty()!!) {
                        binding.etApartment.error = "Please Enter Apartment"
                    } else {
                        binding.etApartment.error = null
                    }
                }
                with(binding.etFlatNo.editText?.text) {
                    if (this?.isEmpty()!!) {
                        binding.etFlatNo.error = "Please Enter Flat"
                    } else {
                        binding.etFlatNo.error = null
                    }
                }
                return@setOnClickListener
            }
        }

        binding.btnBackToCart.setOnClickListener {
            it.findNavController().navigate(R.id.action_addressFragment_to_shoppingCartFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observer() {
        viewModel.addressList.observe(viewLifecycleOwner) {
            adapter = AddressAdapter(it, requireContext())
            rv = binding.rvSelectAddress
            rv.setHasFixedSize(true)
            val linearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            rv.layoutManager = linearLayoutManager
            rv.adapter = adapter
        }
    }
}