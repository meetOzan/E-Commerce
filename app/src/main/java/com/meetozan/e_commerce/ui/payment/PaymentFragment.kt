package com.meetozan.e_commerce.ui.payment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.databinding.FragmentPaymentBinding
import com.meetozan.e_commerce.ui.shopping_cart.ShoppingCartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()
    private val shoppingCartViewModel: ShoppingCartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        binding.btnCompletePayment.setOnClickListener {
            if (binding.btnCompletePayment.text == "Confirm") {

                if (binding.creditCartCardName.editText?.text.toString().isEmpty()) {
                    binding.creditCartCardName.error = "Please Enter Your Name"
                }else{
                    binding.creditCartCardName.error = null
                }

                if (binding.creditCartCardNumber.editText?.text?.length!! < 16) {
                    binding.creditCartCardNumber.error = "Please Enter Your Card Number"
                }else{
                    binding.creditCartCardNumber.error = null
                }

                if (binding.creditCartCardMonth.editText?.text?.length!! < 2) {
                    binding.creditCartCardMonth.error = "Please Enter Month"
                }else{
                    binding.creditCartCardMonth.error = null
                }

                if (binding.creditCartCardYear.editText?.text?.length!! < 2) {
                    binding.creditCartCardYear.error = "Please Enter Year"
                }else{
                    binding.creditCartCardYear.error = null
                }

                if (binding.creditCartCardCvv.editText?.text?.length!! < 3) {
                    binding.creditCartCardCvv.error = "Please Enter CVV"
                }else{
                    binding.creditCartCardCvv.error = null
                }

                if (binding.creditCartCardCvv.editText?.text?.length!! == 3 &&
                    binding.creditCartCardYear.editText?.text?.length!! == 2 &&
                    binding.creditCartCardMonth.editText?.text?.length!! == 2 &&
                    binding.creditCartCardNumber.editText?.text?.length!! == 16 &&
                    binding.creditCartCardName.editText?.text.toString().isNotEmpty()
                ) {

                    binding.tvCreditCardName.text =
                        binding.creditCartCardName.editText?.text.toString()
                    binding.tvCreditCardNumber.text =
                        binding.creditCartCardNumber.editText?.text.toString()
                    binding.tvCreditCardMonth.text =
                        binding.creditCartCardMonth.editText?.text.toString()
                    binding.tvCreditCardYear.text =
                        binding.creditCartCardYear.editText?.text.toString()

                    if (binding.tvCreditCardMonth.text.isNotEmpty() && binding.tvCreditCardYear.text.isNotEmpty()) {
                        binding.tvCreditCardSlash.visibility = View.VISIBLE
                    }
                    binding.btnCompletePayment.setText(R.string.buy)
                }
                else{
                    return@setOnClickListener
                }

                if (binding.btnCompletePayment.text == "Buy Now !!") {

                    if (binding.creditCartCardName.editText?.text.toString().isEmpty()) {
                        binding.creditCartCardName.error = "Please Enter Your Name"
                    }else{
                        binding.creditCartCardName.error = null
                    }

                    if (binding.creditCartCardNumber.editText?.text?.length!! < 16) {
                        binding.creditCartCardNumber.error = "Please Enter Your Card Number"
                    }else{
                        binding.creditCartCardNumber.error = null
                    }

                    if (binding.creditCartCardMonth.editText?.text?.length!! < 2) {
                        binding.creditCartCardMonth.error = "Please Enter Month"
                    }else{
                        binding.creditCartCardMonth.error = null
                    }

                    if (binding.creditCartCardYear.editText?.text?.length!! < 2) {
                        binding.creditCartCardYear.error = "Please Enter Year"
                    }else{
                        binding.creditCartCardYear.error = null
                    }

                    if (binding.creditCartCardCvv.editText?.text?.length!! < 3) {
                        binding.creditCartCardCvv.error = "Please Enter CVV"
                    }else{
                        binding.creditCartCardCvv.error = null
                    }

                    binding.btnCompletePayment.setOnClickListener {
                        if (binding.creditCartCardCvv.editText?.text?.length!! == 3 &&
                            binding.creditCartCardYear.editText?.text?.length!! == 2 &&
                            binding.creditCartCardMonth.editText?.text?.length!! == 2 &&
                            binding.creditCartCardNumber.editText?.text?.length!! == 16 &&
                            binding.creditCartCardName.editText?.text.toString().isNotEmpty()
                        ) {

                            val dialog = LayoutInflater.from(requireContext())
                                .inflate(R.layout.payment_done_dialog, null)
                            val builder = AlertDialog.Builder(context)
                            builder.setView(dialog)
                            dialog.setBackgroundColor(Color.TRANSPARENT)

                            val anim = dialog.findViewById<LottieAnimationView>(R.id.paymentCompletedAnim)
                            val buttonOk = dialog.findViewById<Button>(R.id.btnPaymentCompleted)

                            anim.playAnimation()

                            builder.create().show()

                        } else {
                            binding.btnCompletePayment.setText(R.string.confirm)
                            return@setOnClickListener
                        }
                    }
                }
            }

            binding.btnCompletePayment.setOnClickListener {

                val dialog = LayoutInflater.from(requireContext())
                    .inflate(R.layout.payment_done_dialog, null)
                val builder = AlertDialog.Builder(context).setView(dialog).create()

                val anim = dialog.findViewById<LottieAnimationView>(R.id.paymentCompletedAnim)
                val buttonOk = dialog.findViewById<Button>(R.id.btnPaymentCompleted)

                dialog.background = null

                anim.playAnimation()

                builder.show()

            }
        }
    }

    private fun observer() {
        shoppingCartViewModel.basketList.observe(viewLifecycleOwner) {

            var totalPrice = 0

            for (index in it.indices) {
                totalPrice += it[index].price * it[index].piece
            }

            binding.paymentTotalPrice.text = totalPrice.toString()

        }
    }
}