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
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.databinding.FragmentPaymentBinding
import com.meetozan.e_commerce.ui.shopping_cart.ShoppingCartViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()
    private val shoppingCartViewModel: ShoppingCartViewModel by viewModels()

    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    private val current = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shoppingCartViewModel.basketList.observe(viewLifecycleOwner) {
            var totalPrice = 0
            for (indicex in it.indices) {
                totalPrice += it[indicex].price * it[indicex].piece
            }
            binding.paymentTotalPrice.text = totalPrice.toString()
        }

        binding.btnCompletePayment.setOnClickListener {

            if (binding.btnCompletePayment.text == "Buy Now !!") {

                if (binding.creditCartCardCvv.editText?.text?.length!! == 3 &&
                    binding.creditCartCardYear.editText?.text?.length!! == 2 &&
                    binding.creditCartCardMonth.editText?.text?.length!! == 2 &&
                    binding.creditCartCardNumber.editText?.text?.length!! == 16 &&
                    binding.creditCartCardName.editText?.text.toString().isNotEmpty()
                ) {

                    shoppingCartViewModel.basketList.observe(viewLifecycleOwner) {

                        for (indices in it.indices) {

                            val liveStock = it[indices].stock - it[indices].piece

                            val orderHashMap = hashMapOf<Any, Any>(
                                "id" to it[indices].id,
                                "productName" to it[indices].productName,
                                "price" to it[indices].price,
                                "brand" to it[indices].brand,
                                "picUrl" to it[indices].picUrl,
                                "secondPicUrl" to it[indices].secondPicUrl,
                                "thirdPicUrl" to it[indices].thirdPicUrl,
                                "description" to it[indices].description,
                                "order_status" to "Preparing",
                                "order_time" to current.format(formatter).toString(),
                                "piece" to it[indices].piece,
                                "rate" to it[indices].rate,
                                "stock" to it[indices].stock
                            )

                            viewModel.reduceStock(it[indices].id, liveStock)
                            viewModel.deleteFromBasket(it[indices].productName)
                            viewModel.addToOrders(it[indices].productName, orderHashMap)

                        }
                    }

                    val dialog = LayoutInflater.from(requireContext())
                        .inflate(R.layout.payment_done_dialog, null)
                    val builder = AlertDialog.Builder(context)
                    builder.setView(dialog)
                    dialog.setBackgroundColor(Color.TRANSPARENT)

                    val alertDialog = builder.create()

                    val anim =
                        dialog.findViewById<LottieAnimationView>(R.id.paymentCompletedAnim)
                    val buttonOk = dialog.findViewById<Button>(R.id.btnPaymentCompleted)

                    buttonOk.setOnClickListener {
                        findNavController().navigate(R.id.main_graph)
                        alertDialog.dismiss()
                    }

                    anim.playAnimation()

                    alertDialog.show()

                } else {

                    with(binding.creditCartCardName.editText?.text.toString()) {
                        if (this.isEmpty()) {
                            binding.creditCartCardName.error = "Please Enter Your Name"
                        } else {
                            binding.creditCartCardName.error = null
                        }
                    }

                    with(binding.creditCartCardNumber.editText?.text) {
                        if (this?.length!! < 16) {
                            binding.creditCartCardNumber.error = "Please Enter Card Number"
                        } else {
                            binding.creditCartCardNumber.error = null
                        }
                    }

                    with(binding.creditCartCardMonth.editText?.text) {
                        if (this?.length!! < 2 || 13 < Integer.parseInt(this.toString()) || Integer.parseInt(
                                this.toString()
                            ) < 0
                        ) {
                            binding.creditCartCardNumber.error = "Please Enter Card Month"
                        } else {
                            binding.creditCartCardNumber.error = null
                        }
                    }

                    with(binding.creditCartCardYear.editText?.text) {
                        if (this?.length!! < 2 || 30 < Integer.parseInt(this.toString()) || Integer.parseInt(
                                this.toString()
                            ) < 23
                        ) {
                            binding.creditCartCardNumber.error = "Please Enter Card Year"
                        } else {
                            binding.creditCartCardNumber.error = null
                        }
                    }

                    with(binding.creditCartCardCvv.editText?.text) {
                        if (this?.length!! < 3 || Integer.parseInt(this.toString()) < 100) {
                            binding.creditCartCardNumber.error = "Please Enter Cvv"
                        } else {
                            binding.creditCartCardNumber.error = null
                        }
                    }

                    binding.btnCompletePayment.setText(R.string.confirm)
                    return@setOnClickListener
                }
            }

            if (binding.btnCompletePayment.text == "Confirm") {

                with(binding.creditCartCardName.editText?.text.toString()) {
                    if (this.isEmpty()) {
                        binding.creditCartCardName.error = "Please Enter Your Name"
                    } else {
                        binding.creditCartCardName.error = null
                    }
                }

                with(binding.creditCartCardNumber.editText?.text) {
                    if (this?.length!! < 16) {
                        binding.creditCartCardNumber.error = "Please Enter Card Number"
                    } else {
                        binding.creditCartCardNumber.error = null
                    }
                }

                with(binding.creditCartCardMonth.editText?.text) {
                    if (this?.length!! < 2 || 13 < Integer.parseInt(this.toString()) || Integer.parseInt(
                            this.toString()
                        ) < 0
                    ) {
                        binding.creditCartCardNumber.error = "Please Enter Card Month"
                    } else {
                        binding.creditCartCardNumber.error = null
                    }
                }

                with(binding.creditCartCardYear.editText?.text) {
                    if (this?.length!! < 2 || 30 < Integer.parseInt(this.toString()) || Integer.parseInt(
                            this.toString()
                        ) < 23
                    ) {
                        binding.creditCartCardNumber.error = "Please Enter Card Year"
                    } else {
                        binding.creditCartCardNumber.error = null
                    }
                }

                with(binding.creditCartCardCvv.editText?.text) {
                    if (this?.length!! < 3 || Integer.parseInt(this.toString()) < 100) {
                        binding.creditCartCardNumber.error = "Please Enter Cvv"
                    } else {
                        binding.creditCartCardNumber.error = null
                    }
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
                } else {
                    return@setOnClickListener
                }
            }
        }
    }
}