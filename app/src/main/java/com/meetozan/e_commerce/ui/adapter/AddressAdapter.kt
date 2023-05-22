package com.meetozan.e_commerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.e_commerce.data.model.model.Address
import com.meetozan.e_commerce.databinding.AddressItemBinding

class AddressAdapter(private val addressList: List<Address>)
    :RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    inner class ViewHolder(private var binding: AddressItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(address: Address){
            with(binding){
                addressItem = address
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AddressItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = addressList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(addressList[position])
}