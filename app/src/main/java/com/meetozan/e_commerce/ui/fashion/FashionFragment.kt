package com.meetozan.e_commerce.ui.fashion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.meetozan.e_commerce.databinding.FragmentFashionBinding
import com.meetozan.e_commerce.ui.adapter.ProductAdapter
import com.meetozan.e_commerce.ui.favorites.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FashionFragment : Fragment() {

    private val viewModel: FashionViewModel by viewModels()
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var binding: FragmentFashionBinding
    private val favoritesViewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFashionBinding.inflate(inflater, container, false)
        observer()
        return binding.root
    }

    private fun observer() {
        viewModel.fashionList.observe(viewLifecycleOwner) {
            adapter = ProductAdapter(it,requireContext(),layoutInflater,favoritesViewModel)
            rv = binding.fashionRv
            rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rv.adapter = adapter
        }
    }

}