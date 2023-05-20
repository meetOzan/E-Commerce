package com.meetozan.e_commerce.ui.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.databinding.FragmentAddressBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressFragment : Fragment() {

    private lateinit var binding: FragmentAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNextToPayment.setOnClickListener {
            it.findNavController().navigate(R.id.action_addressFragment_to_paymentFragment)
        }

        binding.btnBackToCart.setOnClickListener {
            it.findNavController().navigate(R.id.action_addressFragment_to_shoppingCartFragment)
        }

    }

}