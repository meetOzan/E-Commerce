package com.meetozan.e_commerce.ui.newest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.meetozan.e_commerce.data.dto.ProductDto
import com.meetozan.e_commerce.databinding.FragmentNewestBinding
import com.meetozan.e_commerce.ui.adapter.ProductAdapter
import com.meetozan.e_commerce.ui.favorites.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewestFragment : Fragment() {

    private var _binding: FragmentNewestBinding? = null
    private val binding get() = _binding!!
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ProductAdapter
    private val viewModel: NewestViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewestBinding.inflate(inflater, container, false)
        observer()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observer() {
        viewModel.newestList.observe(viewLifecycleOwner) {
            adapter = ProductAdapter(
                it as MutableList<ProductDto>,
                requireContext(),
                layoutInflater,
                favoritesViewModel
            )
            rv = binding.newestRv
            rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rv.adapter = adapter
        }
    }
}