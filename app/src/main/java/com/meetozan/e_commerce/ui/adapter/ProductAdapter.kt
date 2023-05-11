package com.meetozan.e_commerce.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.switchmaterial.SwitchMaterial
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.databinding.ProductCardBinding
import com.meetozan.e_commerce.ui.favorites.FavoritesViewModel
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val productList: MutableList<Product>,
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val favoritesViewModel: FavoritesViewModel
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(private var binding: ProductCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                productCard = product

                Picasso.get().load(product.picUrl)
                    .centerCrop()
                    .resize(1000, 1000)
                    .into(productCardImage)

                if (product.stock == 0) {
                    tvOutOfStock.visibility = View.VISIBLE
                    viewOutOfStockGray.visibility = View.VISIBLE
                    cvProduct.isEnabled = false
                }

                cvProduct.setOnClickListener {
                    val imageList = ArrayList<SlideModel>()
                    val dialog = BottomSheetDialog(context)
                    val view = layoutInflater.inflate(R.layout.bottom_sheet_detail, null)

                    imageList.add(SlideModel(product.picUrl))
                    imageList.add(SlideModel(product.secondPicUrl))
                    imageList.add(SlideModel(product.thirdPicUrl))

                    dialog.setContentView(view)

                    view.findViewById<TextView>(R.id.tvDetailName).text = product.productName
                    view.findViewById<TextView>(R.id.tvDetailPrice).text = product.price.toString()
                    view.findViewById<TextView>(R.id.tvDetailBrand).text = product.brand
                    view.findViewById<TextView>(R.id.tvDetailDescription).text = product.description
                    view.findViewById<ImageSlider>(R.id.imageSliderDetail).setImageList(imageList)
                    val basketButton = view.findViewById<ImageButton>(R.id.btnAddToCart)
                    val favSwitch = view.findViewById<SwitchMaterial>(R.id.favoriteSwitch)
                    val stock = view.findViewById<TextView>(R.id.tvDetailStock)
                    stock.text = product.stock.toString()

                    val productHashMap = hashMapOf<Any, Any>(
                        "id" to product.id,
                        "productName" to product.productName,
                        "price" to product.price,
                        "brand" to product.brand,
                        "picUrl" to product.picUrl,
                        "secondPicUrl" to product.secondPicUrl,
                        "thirdPicUrl" to product.thirdPicUrl,
                        "description" to product.description,
                        "isFavorite" to true,
                        "piece" to product.piece,
                        "rate" to product.rate,
                        "stock" to product.stock
                    )

                    if (this.productCard?.stock!! <= 10) {
                        stock.visibility = View.VISIBLE
                        view.findViewById<TextView>(R.id.tvStock).visibility = View.VISIBLE
                    }

                    basketButton.setOnClickListener {
                        favoritesViewModel.addToBasket(product, productHashMap)
                        Toast.makeText(context, "Added to Basket", Toast.LENGTH_SHORT).show()
                    }

                    favSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {
                            buttonView.text = "♥"
                            favoritesViewModel.addToFavorites(product, productHashMap)
                            Toast.makeText(context, "Added to Favs", Toast.LENGTH_SHORT).show()
                        } else {
                            favoritesViewModel.deleteFromFavorites(product)
                            buttonView.text = ""
                            val position = adapterPosition
                            if (position != RecyclerView.NO_POSITION) {
                                this@ProductAdapter.removeFromFavs(position)
                            }
                            Toast.makeText(context, "Removed from Favs", Toast.LENGTH_SHORT).show()
                        }
                    }
                    dialog.show()
                }
            }
        }
    }

    fun removeFromFavs(position: Int) {
        productList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(productList[position])

}