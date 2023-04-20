package com.meetozan.e_commerce.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.databinding.ProductCardBinding
import com.meetozan.e_commerce.ui.favorites.FavoritesViewModel
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val productList: List<Product>,
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val favoritesViewModel: FavoritesViewModel
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(private var binding: ProductCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                productCard = product

                cvProduct.setOnClickListener {
                    val imageList = ArrayList<SlideModel>()
                    val dialog = BottomSheetDialog(context)
                    val view = layoutInflater.inflate(R.layout.bottom_sheet_detail, null)

                    imageList.add(SlideModel(product.picUrl))
                    imageList.add(SlideModel(product.secondPicUrl))
                    imageList.add(SlideModel(product.thirdPicUrl))

                    dialog.setContentView(view)

                    view.findViewById<TextView>(R.id.tvDetailName).text = product.name
                    view.findViewById<TextView>(R.id.tvDetailPrice).text = product.price.toString()
                    view.findViewById<TextView>(R.id.tvDetailBrand).text = product.brand
                    view.findViewById<TextView>(R.id.tvDetailDescription).text = product.description
                    view.findViewById<ImageSlider>(R.id.imageSliderDetail).setImageList(imageList)

                    val favButton = view.findViewById<ImageButton>(R.id.btnAddToFav)
                    val stock = view.findViewById<TextView>(R.id.tvDetailStock)
                    stock.text = product.stock.toString()

                    if (this.productCard?.stock!! <= 10) {
                        stock.visibility = View.VISIBLE
                        view.findViewById<TextView>(R.id.tvStock).visibility = View.VISIBLE
                    }

                    with(favButton) {

                        if (product.isFavorite == true) {
                            setImageResource(R.drawable.ic_filled_favorite)
                            colorFilter = PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)

                        }
                        if (product.isFavorite == false) {
                            setImageResource(R.drawable.ic_empty_favorite)
                            colorFilter =
                                PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                        }

                        setOnClickListener {
                            if (product.isFavorite == true) {
                                favoritesViewModel.deleteFromFavorites(product)
                                setImageResource(R.drawable.ic_empty_favorite)
                                colorFilter =
                                    PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                                favoritesViewModel.updateProduct(product)
                                return@setOnClickListener
                            } else {
                                favoritesViewModel.addToFavorites(product)
                                setImageResource(R.drawable.ic_filled_favorite)
                                colorFilter =
                                    PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                                favoritesViewModel.updateProduct(product)
                                return@setOnClickListener
                            }
                        }
                    }

                    dialog.show()
                }

                Picasso.get().load(product.picUrl)
                    .centerCrop()
                    .resize(500, 500)
                    .into(productCardImage)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(productList[position])

}