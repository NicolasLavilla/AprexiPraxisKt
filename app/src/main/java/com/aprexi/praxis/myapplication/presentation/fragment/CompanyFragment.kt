package com.aprexi.praxis.myapplication.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.aprexi.praxis.myapplication.databinding.FragmentCompanyBinding
import com.aprexi.praxis.myapplication.databinding.FragmentOfferDetailBinding
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CompanyFragment: Fragment() {
    private val binding: FragmentCompanyBinding by lazy {
        FragmentCompanyBinding.inflate(layoutInflater)
    }

    private val args: OfferDetailFragmentArgs by navArgs()
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val offersViewModel: OfferViewModel by activityViewModel()
    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var followOffer: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}