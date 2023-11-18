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
import com.aprexi.praxis.myapplication.databinding.FragmentOfferListBinding
import com.aprexi.praxis.myapplication.model.ListOffersResponse
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.presentation.OfferDetailActivity
import com.aprexi.praxis.myapplication.presentation.adpter.OfferListAdapter
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class OfferListFragment : Fragment() {

    private lateinit var binding: FragmentOfferListBinding
    private lateinit var progressBar: ProgressBar
    private val myUtils: Utils by inject()
    private val offerListAdapter = OfferListAdapter(myUtils)
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
        binding = FragmentOfferListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.pbOfferList
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
                offerViewModel.fetchOfferList(idUser = idUser, loginToken)
            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            myUtils.showErrorDialog(context = requireContext(),e.toString())
        }
    }

    private fun cleanTokenAndRedirectToLogin() {
        tokenViewModel.cleanTokenPreferences()
        myUtils.redirectToLogin(requireContext())
    }

    private fun initViewModel() {
        offerViewModel.getOfferListLiveData().observe(viewLifecycleOwner, this::handleOfferListState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleOfferListState(state: OfferListState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccess(state.result)
            is ResourceState.SuccessFaild -> handleSuccessResponse()
            is ResourceState.Error -> myUtils.showErrorDialog(context = requireContext(),state.error)
            else -> {}
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> myUtils.showProgressBar(false, progressBar)
            is ResourceState.Error -> myUtils.showErrorDialog(context = requireContext(),state.error) { myUtils.redirectToLogin(requireContext()) }
            else -> {}
        }
    }

    private fun handleSuccess(result: ListOffersResponse) {
        myUtils.showProgressBar(false, progressBar)
        offerListAdapter.submitList(result.offer)
    }

    private fun handleSuccessResponse() {
        myUtils.showProgressBar(false, progressBar)
        cleanTokenAndRedirectToLogin()
    }

    private fun initUI() {
        binding.rvOfferList.adapter = offerListAdapter
        binding.rvOfferList.layoutManager = LinearLayoutManager(requireContext())

        offerListAdapter.onClickListener = { offer ->

            val intent = Intent(context, OfferDetailActivity::class.java)
            intent.putExtra("idUser", idUser)
            intent.putExtra("idOffer", offer.idOffer.toInt())
            startActivity(intent)
        }
    }
}
