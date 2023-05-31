package com.meetozan.e_commerce.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.domain.model.data.Address
import com.meetozan.e_commerce.databinding.AddressItemBinding

class AddressAdapter(private val addressList: List<Address>, private val context: Context) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    inner class ViewHolder(private var binding: AddressItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(address: Address) {
            with(binding) {
                addressItem = address

                cvAddressItem.setOnClickListener {

                    it.findNavController()
                        .navigate(R.id.action_addressFragment_to_paymentFragment)
                    Toast.makeText(
                        context,
                        address.name + " named address selected",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    /*
                    if (!address.isDefault!!)  {
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("This address is not your default address !")
                        builder.setMessage("Are you wanted to continue ?")

                        builder.setPositiveButton(R.string.ok) { _, _ ->
                            it.findNavController()
                                .navigate(R.id.action_addressFragment_to_paymentFragment)
                        }

                        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
                            dialog.dismiss()
                        }
                        builder.show()

                    }*/
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AddressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = addressList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(addressList[position])
    }
}