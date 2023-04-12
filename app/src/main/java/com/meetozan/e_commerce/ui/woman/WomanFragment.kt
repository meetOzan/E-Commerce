package com.meetozan.e_commerce.ui.woman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.meetozan.e_commerce.databinding.FragmentWomanBinding
import com.meetozan.e_commerce.ui.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WomanFragment : Fragment() {

    private lateinit var binding: FragmentWomanBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var rv: RecyclerView
    private val viewModel: WomanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWomanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
    }

    private fun observer() {
        viewModel.womanList.observe(viewLifecycleOwner) {
            adapter = ProductAdapter(it)
            rv = binding.womanRv
            rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rv.adapter = adapter
        }
    }

}