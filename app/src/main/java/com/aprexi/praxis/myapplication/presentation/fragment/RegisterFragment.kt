package com.aprexi.praxis.myapplication.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aprexi.praxis.myapplication.databinding.FragmentRegisterBinding

class RegisterFragment: Fragment() {

    private val binding: FragmentRegisterBinding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
    }
    /*
        private val user: Int = 4

        private val offerListAdapter = OfferListAdapter()

        private val offerViewModel: OfferViewModel by activityViewModel()*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}