package com.meetozan.e_commerce.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.meetozan.e_commerce.R
import com.meetozan.e_commerce.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        binding.imageCurrentOrders.playAnimation()

        binding.imageNoCurrentOrders.playAnimation()

        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
            it.findNavController().navigate(R.id.nav_graph)
        }

        binding.btnGoHome.setOnClickListener{
            it.findNavController().navigate(R.id.homeFragment)
        }

    }

    private fun observer(){
        viewModel.user.observe(viewLifecycleOwner){
            binding.tvProfileName.text = it.username

            binding.tvProfileGender.let {gender->
                if(it.gender == "Male"){
                    gender.text = it.gender
                    gender.setTextColor(ContextCompat.getColor(requireContext(),R.color.secondaryColor))
                    binding.profileImageView.setBackgroundResource(R.drawable.blue_circle)
                    binding.profileImageView.setImageResource(R.drawable.ic_person)
                }
                if(it.gender == "Female"){
                    gender.text = it.gender
                    gender.setTextColor(ContextCompat.getColor(requireContext(),R.color.pink))
                    binding.profileImageView.setBackgroundResource(R.drawable.pink_circle)
                    binding.profileImageView.setImageResource(R.drawable.ic_person_woman)
                }
            }
        }
    }
}