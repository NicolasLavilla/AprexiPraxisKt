package com.aprexi.praxis.myapplication.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentCompanyBinding
import com.aprexi.praxis.myapplication.model.Company
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.CompanyState
import com.aprexi.praxis.myapplication.presentation.viewmodel.CompanyViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CompanyFragment: Fragment() {

    private lateinit var binding: FragmentCompanyBinding
    private lateinit var progressBar: ProgressBar
    private val myUtils: Utils by inject()
    private val args: CompanyFragmentArgs by navArgs()
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val companyViewModel: CompanyViewModel by activityViewModel()
    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var followOffer: Boolean = false
    private lateinit var company: Company

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.pbCompanyFragment
        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()
    }

    private fun getTokenLoginPreference(){
        lifecycleScope.launch {
            val uni = tokenViewModel.fetchLoginTokenPreferences()
            loginToken = uni.token
            succesToken = uni.success
        }
    }

    private fun handleAuthentication() {
        try {
            if (succesToken) {
                tokenViewModel.fetchCheckToken(loginToken)
                companyViewModel.fetchCompany(
                    idUser = args.idUser,
                    idCompany = args.idCompany,
                    token = loginToken
                )
            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            myUtils.showError(requireContext() ,e.toString())
        }
    }

    private fun initViewModel() {
        companyViewModel.getCompanyLiveData().observe(viewLifecycleOwner, this::handleCompanyState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleCompanyState(state: CompanyState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true,progressBar)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> { }
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true,progressBar)
            is ResourceState.Success -> myUtils.showProgressBar(false,progressBar)
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error) { cleanTokenAndRedirectToLogin() }
            else -> { }
        }
    }

    private fun handleSuccessOfferDetail(companyRequest: Company) {
        myUtils.showProgressBar(false,progressBar)
        company = companyRequest
        initUI()
    }

    private fun handleSuccessFailed() {
        myUtils.showProgressBar(false,progressBar)
        cleanTokenAndRedirectToLogin()
    }

    private fun cleanTokenAndRedirectToLogin() {
        tokenViewModel.cleanTokenPreferences()
        myUtils.redirectToLogin(requireContext())
    }

    private fun initUI() {

        val tabLayout = binding.tlCompanyTabs
        val viewPager = binding.vp2CompanyViewpager

        viewPager.adapter = OfferViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = when (position){
                0 -> getString(R.string.description_company)
                1 -> getString(R.string.offers_company)
                else -> getString(R.string.error)
            }
        }.attach()

        Glide.with(requireContext())
            .load(company.logoCompany)
            .into(binding.ivLogoTbOfferDetail)

        binding.tvTitleOfferDetailFragment.text = company.nameCompany

        binding.vBackBottomCompany.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private inner class OfferViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment){
        override fun getItemCount() = 2
        override fun createFragment(position: Int): Fragment{
            return when(position){
                0 -> DescriptionCompanyFragment(company)
                1 -> OfferListCompanyFragment(company.idCompany.toInt())
                else -> throw IllegalAccessException("Invalid position")
            }
        }
    }
}