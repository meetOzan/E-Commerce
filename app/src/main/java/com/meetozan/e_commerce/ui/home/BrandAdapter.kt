package com.meetozan.e_commerce.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.e_commerce.data.model.Brand
import com.meetozan.e_commerce.databinding.BrandItemBinding
import com.squareup.picasso.Picasso

class BrandAdapter(private val brandList: List<Brand>) :
    RecyclerView.Adapter<BrandAdapter.ViewHolder>() {

    class ViewHolder(binding: BrandItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val brandItemCard: BrandItemBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BrandItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = brandList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val brandItem = brandList[position]
        holder.brandItemCard.brandItemCard = brandItem

        Picasso.get().load(brandItem.picUrl)
            .resize(500,500)
            .centerCrop()
            .into(holder.brandItemCard.brandPic)

    }
}