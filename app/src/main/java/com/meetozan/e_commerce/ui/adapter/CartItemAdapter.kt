package com.meetozan.e_commerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.databinding.ItemCartBinding
import com.meetozan.e_commerce.ui.shopping_cart.ShoppingCartViewModel
import com.squareup.picasso.Picasso

class CartItemAdapter(
    private val cartList: List<Product>,
    private val cartViewModel: ShoppingCartViewModel
) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartProduct: Product) {
            with(binding) {
                cartItem = cartProduct

                Picasso.get().load(cartProduct.picUrl)
                    .centerCrop()
                    .resize(500, 500)
                    .into(imageCartItem)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(cartList[position])

}