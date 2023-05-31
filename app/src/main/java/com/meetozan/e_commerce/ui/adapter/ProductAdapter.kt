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
import com.meetozan.e_commerce.data.dto.ProductDto
import com.meetozan.e_commerce.databinding.ProductCardBinding
import com.meetozan.e_commerce.ui.favorites.FavoritesViewModel
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val productDtoList: MutableList<ProductDto>,
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val favoritesViewModel: FavoritesViewModel
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(private var binding: ProductCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productDto: ProductDto) {
            with(binding) {
                productCard = productDto

                Picasso.get().load(productDto.picUrl)
                    .centerCrop()
                    .resize(1000, 1000)
                    .into(productCardImage)

                if (productDto.stock == 0) {
                    tvOutOfStock.visibility = View.VISIBLE
                    viewOutOfStockGray.visibility = View.VISIBLE
                    cvProduct.isEnabled = false
                }

                cvProduct.setOnClickListener {
                    val imageList = ArrayList<SlideModel>()
                    val dialog = BottomSheetDialog(context)
                    val view = layoutInflater.inflate(R.layout.bottom_sheet_detail, null)

                    imageList.add(SlideModel(productDto.picUrl))
                    imageList.add(SlideModel(productDto.secondPicUrl))
                    imageList.add(SlideModel(productDto.thirdPicUrl))

                    dialog.setContentView(view)

                    view.findViewById<TextView>(R.id.tvDetailName).text = productDto.productName
                    view.findViewById<TextView>(R.id.tvDetailPrice).text = productDto.price.toString()
                    view.findViewById<TextView>(R.id.tvDetailBrand).text = productDto.brand
                    view.findViewById<TextView>(R.id.tvDetailDescription).text = productDto.description
                    view.findViewById<ImageSlider>(R.id.imageSliderDetail).setImageList(imageList)
                    val basketButton = view.findViewById<ImageButton>(R.id.btnAddToCart)
                    val favSwitch = view.findViewById<SwitchMaterial>(R.id.favoriteSwitch)
                    val stock = view.findViewById<TextView>(R.id.tvDetailStock)
                    stock.text = productDto.stock.toString()

                    val productHashMap = hashMapOf<Any, Any>(
                        "id" to productDto.id,
                        "productName" to productDto.productName,
                        "price" to productDto.price,
                        "brand" to productDto.brand,
                        "picUrl" to productDto.picUrl,
                        "secondPicUrl" to productDto.secondPicUrl,
                        "thirdPicUrl" to productDto.thirdPicUrl,
                        "description" to productDto.description,
                        "isFavorite" to true,
                        "piece" to productDto.piece,
                        "rate" to productDto.rate,
                        "stock" to productDto.stock
                    )

                    if (this.productCard?.stock!! <= 10) {
                        stock.visibility = View.VISIBLE
                        view.findViewById<TextView>(R.id.tvStock).visibility = View.VISIBLE
                    }

                    basketButton.setOnClickListener {
                        favoritesViewModel.addToBasket(productDto, productHashMap)
                        Toast.makeText(context, "Added to Basket", Toast.LENGTH_SHORT).show()
                    }

                    favSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {
                            buttonView.text = "♥"
                            favoritesViewModel.addToFavorites(productDto, productHashMap)
                            Toast.makeText(context, "Added to Favs", Toast.LENGTH_SHORT).show()
                        } else {
                            favoritesViewModel.deleteFromFavorites(productDto)
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
        productDtoList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = productDtoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(productDtoList[position])

}