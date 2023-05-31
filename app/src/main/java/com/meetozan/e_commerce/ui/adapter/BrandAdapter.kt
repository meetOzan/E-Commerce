package com.meetozan.e_commerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.e_commerce.data.dto.BrandDto
import com.meetozan.e_commerce.databinding.BrandItemBinding
import com.squareup.picasso.Picasso

class BrandAdapter(private val brandDtoList: List<BrandDto>) :
    RecyclerView.Adapter<BrandAdapter.ViewHolder>() {

    class ViewHolder(binding: BrandItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val brandItemCard: BrandItemBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BrandItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = brandDtoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val brandItem = brandDtoList[position]
        holder.brandItemCard.brandItemCard = brandItem

        Picasso.get().load(brandItem.picUrl)
            .resize(500,500)
            .centerCrop()
            .into(holder.brandItemCard.brandPic)

    }
}