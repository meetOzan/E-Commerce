package com.meetozan.e_commerce.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.e_commerce.domain.model.data.Address
import com.meetozan.e_commerce.databinding.AddressItemBinding
import com.meetozan.e_commerce.ui.address.AddressViewModel

class ProfileAddressAdapter(private val addressList : List<Address>, private val context: Context, private val addressViewModel: AddressViewModel)
    :RecyclerView.Adapter<ProfileAddressAdapter.ViewHolder>(){

    inner class ViewHolder(private var binding: AddressItemBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Address){
            with(binding){
                addressItem = address

                if(address.isDefault==true){
                    cvAddressItem.strokeWidth = 2
                }

                cvAddressItem.setOnClickListener {

                    val dialog = AlertDialog.Builder(context)
                    dialog.setTitle(address.name)
                    dialog.setMessage("You can delete or make default address" + address.name)

                    dialog.setPositiveButton("Make Default"){ alertDialog, _ ->
                        for (index in addressList.indices){
                            addressViewModel.updateAddress(addressList[index].name,"isDefault",false)
                        }
                        alertDialog.dismiss()
                    }

                    dialog.setNegativeButton("Delete"){alertDialog,_ ->
                        addressViewModel.deleteAddress(address.name)
                        alertDialog.dismiss()
                    }
                    dialog.show()
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AddressItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = addressList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(addressList[position])
    }


}