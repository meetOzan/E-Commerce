package com.meetozan.e_commerce.ui.home.newest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meetozan.e_commerce.databinding.FragmentNewestBinding

class NewestFragment : Fragment() {

    private lateinit var binding: FragmentNewestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewestBinding.inflate(inflater, container, false)
        return binding.root
    }

}