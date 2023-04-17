package com.meetozan.e_commerce.ui.cosmetic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.meetozan.e_commerce.databinding.FragmentCosmeticBinding
import com.meetozan.e_commerce.ui.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CosmeticFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var binding: FragmentCosmeticBinding
    private val viewModel: CosmeticViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCosmeticBinding.inflate(inflater, container, false)
        observer()
        return binding.root
    }

    private fun observer() {
        viewModel.cosmeticList.observe(viewLifecycleOwner) {
            adapter = ProductAdapter(it,requireContext(),layoutInflater)
            rv = binding.cosmeticRv
            rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rv.adapter = adapter
        }
    }

}