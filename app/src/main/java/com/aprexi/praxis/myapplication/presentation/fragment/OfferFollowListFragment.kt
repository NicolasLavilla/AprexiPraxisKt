package com.aprexi.praxis.myapplication.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentOffersFollowBinding
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.presentation.SplashActivity
import com.aprexi.praxis.myapplication.presentation.adpter.OfferListAdapter
import com.aprexi.praxis.myapplication.presentation.viewmodel.FollowOfferListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferFollowViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class OfferFollowListFragment: Fragment() {

    private val binding: FragmentOffersFollowBinding by lazy {
        FragmentOffersFollowBinding.inflate(layoutInflater)
    }

    private val offerListAdapter = OfferListAdapter()
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val offerFollowViewModel: OfferFollowViewModel by activityViewModel()
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

    private fun initViewModel() {
        offerFollowViewModel.getFollowOfferListLiveData().observe(viewLifecycleOwner, this::handleOfferListState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleOfferListState(state: FollowOfferListState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccess(state.result)
            is ResourceState.SuccessFaild -> handleSuccessResponse()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleSuccess(result: ListOffersResponse) {
        showProgressBar(false)
        offerListAdapter.submitList(result.offer)
    }

    private fun handleSuccessResponse() {
        showProgressBar(false)
        cleanTokenAndRedirectToLogin()
    }

    private fun initUI() {
        binding.rvOfferListFollowOffersFragment.adapter = offerListAdapter
        binding.rvOfferListFollowOffersFragment.layoutManager = LinearLayoutManager(requireContext())

        offerListAdapter.onClickListener = { offer ->

            findNavController().navigate(
                OfferFollowListFragmentDirections.actionOfferFollowFragmentToOfferDetailFragment(
                    idUser = idUser,
                    idOffer = offer.idOffer.toInt(),
                )
            )
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

    private fun showProgressBar(show: Boolean) {
        binding.pbFollowOfferList.visibility = if (show) View.VISIBLE else View.GONE
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
                offerFollowViewModel.fetchFollowOfferList(idUser = idUser, loginToken)
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

    private fun redirectToLogin() {
        val intent = Intent(requireContext(), SplashActivity::class.java)
        startActivity(intent)
    }

    private fun showErrorDialog(error: String, action: (() -> Unit)? = null) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error)
            .setMessage(error)
            .setPositiveButton(R.string.action_ok) { _, _ -> action?.invoke() }
            .show()
    }
}