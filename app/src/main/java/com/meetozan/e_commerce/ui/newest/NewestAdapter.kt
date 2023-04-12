package com.meetozan.e_commerce.ui.newest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.e_commerce.data.model.Product
import com.meetozan.e_commerce.databinding.ProductCardBinding
import com.squareup.picasso.Picasso

class NewestAdapter(private val newList: List<Product>) :
    RecyclerView.Adapter<NewestAdapter.ViewHolder>() {

    class ViewHolder(binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val productItemCard: ProductCardBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = newList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val productItem = newList[position]
        holder.productItemCard.productCard = productItem

        Picasso.get().load(productItem.picUrl)
            .centerCrop()
            .resize(500, 500)
            .into(holder.productItemCard.productCardImage)

    }
}