package com.meetozan.e_commerce.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.common.Constants.ANIMATION_TIME


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(requireContext().mainLooper).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
        }, ANIMATION_TIME)
    }

}