package com.meetozan.e_commerce.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.meetozan.e_commerce.databinding.FragmentSearchBinding
import com.meetozan.e_commerce.ui.adapter.ProductAdapter
import com.meetozan.e_commerce.ui.favorites.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private val searchViewModel : SearchViewModel by viewModels()
    private val favoritesViewModel : FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
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
                if (newText!=null){
                    with(binding.searchRv){
                        setHasFixedSize(true)
                        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        searchViewModel.searchProduct(newText)
                        searchViewModel.searchList.observe(viewLifecycleOwner) {
                            val adapter =
                                ProductAdapter(it, requireContext(), layoutInflater, favoritesViewModel)
                            this.adapter = adapter
                        }
                    }
                }
                return true
            }
        })
    }
}