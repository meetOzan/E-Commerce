package com.meetozan.e_commerce.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.tabs.TabLayoutMediator
import com.meetozan.e_commerce.databinding.FragmentHomeBinding
import com.meetozan.e_commerce.ui.adapter.BrandAdapter
import com.meetozan.e_commerce.ui.home.viewpager.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var rv: RecyclerView
    private lateinit var adapter: BrandAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        observer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adUrlList = ArrayList<SlideModel>()
        val tabLayoutList = arrayListOf(
            "Newest🔥",
            "Man",
            "Woman",
            "Fashion",
            "Electronics",
            "Home & Furniture",
            "Cosmetics"
        )

        binding.productViewPager.adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
        binding.productViewPager.offscreenPageLimit = 100

        TabLayoutMediator(binding.homeTabLayout, binding.productViewPager,true,true) { tab, position ->
            tab.text = tabLayoutList[position]
        }.attach()

        adUrlList.add(SlideModel("https://cdn.dsmcdn.com/mnresize/500/500/marketing/datascience/automation/2023/4/6/Tobb_Tepebanner_0304231_202304061225.png"))
        adUrlList.add(SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAC0xfcwCnFdvSmEy1bf0OSvm2f1K4ERjCkQ&usqp=CAU"))
        adUrlList.add(SlideModel("https://i0.wp.com/blog.elverys.ie/app/uploads/2018/06/NIKE-BANNERS-1200X300.jpg?fit=1200%2C300"))
        adUrlList.add(SlideModel("https://images.hepsiburada.net/assets/gif/hepsiburada/samsungstore20/assets/img/home/slider/1072-x-325-samsung-hepsiburada-top-banner-desktop-1@2x.jpg"))
        adUrlList.add(SlideModel("https://cache.tradeinn.com/images/brand-page/banner_1707.jpg"))

        binding.adImageSlider.setImageList(adUrlList)

    }

    private fun observer() {
        homeViewModel.brandList.observe(viewLifecycleOwner) {
            adapter = BrandAdapter(it)
            rv = binding.brandRecyclerView
            rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rv.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}