package com.aprexi.praxis.myapplication.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentMyOffersBinding
import com.google.android.material.tabs.TabLayoutMediator

class MyOffersFragment: Fragment() {

    private val binding: FragmentMyOffersBinding by lazy {
        FragmentMyOffersBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI(){
        val tabLayout = binding.tlMyOffersTabs
        val viewPager = binding.vp2MyOfferViewpager

        viewPager.adapter = OfferViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = when (position){
                0 -> getString(R.string.inscriptions_my_offers_fragment)
                1 -> getString(R.string.follow_my_offers_fragment)
                else -> getString(R.string.error)
            }
        }.attach()
    }

    private inner class OfferViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment){
        override fun getItemCount() = 2
        override fun createFragment(position: Int): Fragment{
            return when(position){
                0 -> OfferRequestListFragment()
                1 -> OfferFollowListFragment()
                else -> throw IllegalAccessException("Invalid position")
            }
        }
    }
}