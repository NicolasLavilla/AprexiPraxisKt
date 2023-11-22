package com.aprexi.praxis.myapplication.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aprexi.praxis.myapplication.databinding.FragmentOfferRequestBinding
import com.aprexi.praxis.myapplication.model.ListRequestOffer
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.presentation.DetailRequestOfferActivity
import com.aprexi.praxis.myapplication.presentation.adpter.RequestOfferListAdapter
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferRequestViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.RequestOfferListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class OfferRequestListFragment: Fragment() {

    private lateinit var binding: FragmentOfferRequestBinding
    private lateinit var progressBar: ProgressBar
    private val myUtils: Utils by inject()
    private val offerListAdapter = RequestOfferListAdapter(myUtils)
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val offerRequestListViewModel: OfferRequestViewModel by activityViewModel()
    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var idUser: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOfferRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.pbRequestOfferList
        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()
        initUI()
    }

    private fun getTokenLoginPreference(){
        lifecycleScope.launch {
            val uni = tokenViewModel.fetchLoginTokenPreferences()
            loginToken = uni.token
            succesToken = uni.success
            idUser = uni.idUser
        }
    }

    private fun handleAuthentication() {
        try {
            if (succesToken) {
                tokenViewModel.fetchCheckToken(loginToken)
                offerRequestListViewModel.fetchRequestOfferList(
                    idUser = idUser,
                    token = loginToken
                )
            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            myUtils.showError(context = requireContext(),e.toString())
        }
    }

    private fun initViewModel() {
        offerRequestListViewModel.getRequestOfferListLiveData().observe(viewLifecycleOwner, this::handleOfferDetailState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleOfferDetailState(state: RequestOfferListState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccess(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(context = requireContext(),state.error)
            else -> { }
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> myUtils.showProgressBar(false, progressBar)
            is ResourceState.Error -> myUtils.showError(context = requireContext(),state.error) { cleanTokenAndRedirectToLogin() }
            else -> { }
        }
    }

    private fun handleSuccessFailed() {
        myUtils.showProgressBar(false, progressBar)
        cleanTokenAndRedirectToLogin()
    }

    private fun handleSuccess(result: ListRequestOffer) {
        myUtils.showProgressBar(false, progressBar)
        offerListAdapter.submitList(result.requestOffer)
    }


    private fun initUI() {
        binding.rvOfferRequestListOffersFragment.adapter = offerListAdapter
        binding.rvOfferRequestListOffersFragment.layoutManager = LinearLayoutManager(requireContext())

        offerListAdapter.onClickListener = { offer ->

            val intent = Intent(requireContext(), DetailRequestOfferActivity::class.java)
            intent.putExtra("idUser", idUser)
            intent.putExtra("idOffer", offer.idOffer.toInt())
            startActivity(intent)

        }
    }

    private fun cleanTokenAndRedirectToLogin() {
        tokenViewModel.cleanTokenPreferences()
        myUtils.redirectToLogin(context = requireContext())
    }

    override fun onResume() {
        super.onResume()
        handleAuthentication()
    }
}