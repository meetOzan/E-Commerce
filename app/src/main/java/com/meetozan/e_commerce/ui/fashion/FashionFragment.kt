package com.meetozan.e_commerce.ui.fashion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meetozan.e_commerce.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FashionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fashion, container, false)
    }

}