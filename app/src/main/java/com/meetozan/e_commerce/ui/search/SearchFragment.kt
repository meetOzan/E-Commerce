package com.meetozan.e_commerce.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.meetozan.e_commerce.data.model.model.Product
import com.meetozan.e_commerce.databinding.FragmentSearchBinding
import com.meetozan.e_commerce.ui.adapter.ProductAdapter
import com.meetozan.e_commerce.ui.favorites.FavoritesViewModel
import com.meetozan.e_commerce.ui.newest.NewestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: ProductAdapter
    private val newestViewModel: NewestViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        observer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {

                    with(binding.searchRv) {
                        setHasFixedSize(true)
                        layoutManager =
                            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        searchViewModel.searchProduct(newText)
                        searchViewModel.searchList.observe(viewLifecycleOwner) {
                            if (it.isNullOrEmpty()) {
                                binding.cvSearchNotFound.visibility = View.VISIBLE
                                binding.searchRv.visibility = View.GONE
                            } else {
                                binding.cvSearchNotFound.visibility = View.GONE
                                binding.searchRv.visibility = View.VISIBLE

                                productAdapter =
                                    ProductAdapter(
                                        it as MutableList<Product>,
                                        requireContext(),
                                        layoutInflater,
                                        favoritesViewModel
                                    )
                                this.adapter = productAdapter

                            }
                        }
                    }
                }
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observer() {
        newestViewModel.newestList.observe(viewLifecycleOwner) { list ->
            binding.searchRv.let {
                it.setHasFixedSize(true)
                it.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                productAdapter =
                    ProductAdapter(
                        list as MutableList<Product>,
                        requireContext(),
                        layoutInflater,
                        favoritesViewModel
                    )
                it.adapter = productAdapter
            }
        }
    }
}