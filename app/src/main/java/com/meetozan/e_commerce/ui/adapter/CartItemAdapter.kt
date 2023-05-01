package com.meetozan.e_commerce.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.databinding.ItemCartBinding
import com.meetozan.e_commerce.ui.shopping_cart.ShoppingCartViewModel
import com.squareup.picasso.Picasso

class CartItemAdapter(
    private val cartList: List<Product>,
    private val cartViewModel: ShoppingCartViewModel,
    val context: Context,
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

                addItem.setOnClickListener {
                    var piece = Integer.parseInt(itemCount.text.toString())
                    piece += 1
                    itemCount.text = piece.toString()
                    cartViewModel.updatePiece(piece,cartProduct)
                }

                removeItem.setOnClickListener {
                    var piece = Integer.parseInt(itemCount.text.toString())

                    if (piece <= 1){
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("Product will be deleted")

                        builder.setMessage("Are you sure you want to delete the product from your cart? ")

                        builder.setPositiveButton("OK") { dialog, _ ->
                            cartViewModel.deleteProduct(cartProduct)
                            dialog.dismiss()
                        }

                        builder.setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                        val dialog = builder.create()
                        dialog.show()

                        return@setOnClickListener
                    }

                    piece -= 1
                    itemCount.text = piece.toString()
                    cartViewModel.updatePiece(piece,cartProduct)
                }
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