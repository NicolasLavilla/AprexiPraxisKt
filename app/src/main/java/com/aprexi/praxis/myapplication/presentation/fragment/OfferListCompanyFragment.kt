package com.aprexi.praxis.myapplication.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentOfferListCompanyBinding
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.presentation.OfferDetailActivity
import com.aprexi.praxis.myapplication.presentation.SplashActivity
import com.aprexi.praxis.myapplication.presentation.adpter.OfferListAdapter
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class OfferListCompanyFragment(private val idCompany: Int): Fragment() {

    private val binding: FragmentOfferListCompanyBinding by lazy {
        FragmentOfferListCompanyBinding.inflate(layoutInflater)
    }

    private val offerListCompanyAdapter = OfferListAdapter()
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val offerViewModel: OfferViewModel by activityViewModel()
    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var idUser: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()
        initUI()

    }

    private fun getTokenLoginPreference(){
        lifecycleScope.launch {
            val tokenPreferences = tokenViewModel.fetchLoginTokenPreferences()
            loginToken = tokenPreferences.token
            succesToken = tokenPreferences.success
            idUser = tokenPreferences.idUser
        }
    }

    private fun handleAuthentication() {
        try {
            if (succesToken) {
                tokenViewModel.fetchCheckToken(loginToken)
                offerViewModel.fetchOfferListCompany(idUser = idUser, idCompany = idCompany , token = loginToken)
            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            showErrorDialog(e.toString())
        }
    }

    private fun cleanTokenAndRedirectToLogin() {
        tokenViewModel.cleanTokenPreferences()
        redirectToLogin()
    }

    private fun initViewModel() {
        offerViewModel.getOfferListCompanyLiveData().observe(viewLifecycleOwner, this::handleOfferListState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleOfferListState(state: OfferListState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccess(state.result)
            is ResourceState.SuccessFaild -> handleSuccessResponse()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> showProgressBar(false)
            is ResourceState.Error -> showErrorDialog(state.error) { redirectToLogin() }
            else -> {}
        }
    }

    private fun handleSuccess(result: ListOffersResponse) {
        showProgressBar(false)
        offerListCompanyAdapter.submitList(result.offer)
    }

    private fun handleSuccessResponse() {
        showProgressBar(false)
        cleanTokenAndRedirectToLogin()
    }

    private fun initUI() {
        binding.rvOfferListCompany.adapter = offerListCompanyAdapter
        binding.rvOfferListCompany.layoutManager = LinearLayoutManager(requireContext())

        offerListCompanyAdapter.onClickListener = { offer ->

            val intent = Intent(context, OfferDetailActivity::class.java)
            intent.putExtra("idUser", idUser)
            intent.putExtra("idOffer", offer.idOffer.toInt())
            startActivity(intent)
        }
    }

    private fun redirectToLogin() {
        val intent = Intent(requireContext(), SplashActivity::class.java)
        startActivity(intent)
    }

    private fun showProgressBar(show: Boolean) {
        binding.pbOfferListCompany.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showErrorDialog(error: String, action: (() -> Unit)? = null) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error)
            .setMessage(error)
            .setPositiveButton(R.string.action_ok) { _, _ -> action?.invoke() }
            .show()
    }

}