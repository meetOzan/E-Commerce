package com.meetozan.e_commerce.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.databinding.ItemCartBinding
import com.meetozan.e_commerce.ui.shopping_cart.ShoppingCartViewModel
import com.squareup.picasso.Picasso

class CartItemAdapter(
    private var cartList: MutableList<Product>,
    private val cartViewModel: ShoppingCartViewModel,
    val context: Context
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

                if (cartProduct.stock <= 5) {
                    val strokeColor = Color.RED

                    cvCartItem.strokeWidth = 4
                    cvCartItem.strokeColor = strokeColor
                }

                addItem.setOnClickListener {
                    var piece = Integer.parseInt(itemCount.text.toString())

                    if (Integer.parseInt(itemCount.text.toString()) >= cartProduct.stock) {
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("No Stock")

                        builder.setMessage("We don't have enough stock but will be soon")

                        builder.setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        val dialog = builder.create()
                        dialog.show()
                    } else {
                        piece += 1
                        itemCount.text = piece.toString()
                        cartViewModel.updateBasketItem(piece, cartProduct, "piece")
                    }
                }

                removeItem.setOnClickListener { view ->
                    var piece = Integer.parseInt(itemCount.text.toString())

                    if (piece <= 1) {
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("Product will be deleted")

                        builder.setMessage("Are you sure you want to delete the product from your cart?")

                        builder.setPositiveButton("OK") { dialog, _ ->
                            cartViewModel.deleteProduct(cartProduct)
                            val position = adapterPosition
                            if (position != RecyclerView.NO_POSITION) {
                                this@CartItemAdapter.removeItem(position)
                            }
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
                    cartViewModel.updateBasketItem(piece, cartProduct, "piece")
                }
            }
        }
    }

    fun removeItem(position: Int) {
        cartList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(cartList[position])

}