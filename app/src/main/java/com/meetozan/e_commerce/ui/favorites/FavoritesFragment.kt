package com.meetozan.e_commerce.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.databinding.FragmentFavoritesBinding
import com.meetozan.e_commerce.ui.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var rv: RecyclerView
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        observer()
        binding.favoritesLayout.invalidate()
        return binding.root
    }

    private fun observer(){
        viewModel.favoritesList.observe(viewLifecycleOwner){
            adapter = ProductAdapter(it as MutableList<Product>,requireContext(),layoutInflater,this.viewModel)
            rv = binding.favoritesRv
            rv.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            rv.adapter = adapter
        }
    }

}