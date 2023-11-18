package com.aprexi.praxis.myapplication.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.aprexi.praxis.myapplication.databinding.FragmentDetailRequestOfferBinding
import com.aprexi.praxis.myapplication.model.ListDetailRequestOffer
import com.aprexi.praxis.myapplication.model.Offer
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.presentation.OfferDetailActivity
import com.aprexi.praxis.myapplication.presentation.adpter.DetailRequestOfferListAdapter
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailRequestOfferListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailRequestOfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class DetailRequestOfferFragment: Fragment() {

    private lateinit var binding: FragmentDetailRequestOfferBinding
    private lateinit var progressBar: ProgressBar
    private val myUtils: Utils by inject()
    private val offerListAdapter = DetailRequestOfferListAdapter(myUtils)
    private val args: DetailRequestOfferFragmentArgs by navArgs()
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val detailRequestOfferViewModel: DetailRequestOfferViewModel by activityViewModel()
    private val offerViewModel: OfferViewModel by activityViewModel()
    private var loginToken: String = ""
    private var succesToken: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailRequestOfferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.pbDetailRequestOfferFragment
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
                offerViewModel.fetchOffer(
                    idUser = args.idUser,
                    idOffer = args.idOffer,
                    token = loginToken
                )
                detailRequestOfferViewModel.fetchDetailRequestOfferList(
                    idUser = args.idUser,
                    idOffer = args.idOffer,
                    token = loginToken
                )
            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            myUtils.showErrorDialog(requireContext() ,e.toString())
        }
    }

    private fun initViewModel() {
        offerViewModel.getOfferDetailLiveData().observe(viewLifecycleOwner, this::OfferDetailState)
        detailRequestOfferViewModel.getDetailRequestOfferListLiveData().observe(viewLifecycleOwner, this::DetailRequestOfferListState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }


    private fun OfferDetailState(state: OfferDetailState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleOfferDetailSuccess(state.result) //initUI()
            is ResourceState.SuccessFaild -> handleSuccessResponse()
            is ResourceState.Error ->  myUtils.showErrorDialog(requireContext() ,state.error)
            else -> { }
        }
    }

    private fun handleOfferDetailSuccess(offer: Offer) {
        myUtils.showProgressBar(false, progressBar)
        initUI(offer)
    }

    private fun DetailRequestOfferListState(state: DetailRequestOfferListState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleDetailRequestOfferListSuccess(state.result)
            is ResourceState.SuccessFaild -> handleSuccessResponse()
            is ResourceState.Error ->  myUtils.showErrorDialog(requireContext() ,state.error)
            else -> { }
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> myUtils.showProgressBar(false, progressBar)
            is ResourceState.Error ->  myUtils.showErrorDialog(requireContext() ,state.error) { cleanTokenAndRedirectToLogin() }
            else -> {}
        }
    }

    private fun handleDetailRequestOfferListSuccess(result: ListDetailRequestOffer) {
        myUtils.showProgressBar(false, progressBar)
        offerListAdapter.submitList(result.detailRequestOffer)
    }

    private fun handleSuccessResponse() {
        myUtils.showProgressBar(false, progressBar)
        cleanTokenAndRedirectToLogin()
    }

    private fun initUI(offer: Offer) {
        binding.rvDetailRequestOfferListFragment.adapter = offerListAdapter
        binding.rvDetailRequestOfferListFragment.layoutManager = LinearLayoutManager(requireContext())

        binding.tvTitleOfferDetailRequestItem.text = offer.offerTitle
        binding.tvNameCompanyDetailRequestOfferItem.text = offer.nameCompany
        binding.tvNumInscriptionsDetailRequestItem.text = offer.numRegistered.toString()

        Glide.with(requireContext())
            .load(offer.logoCompany)
            .into(binding.ivOfferListItem)

        binding.cvDetailOfferRequest.setOnClickListener{
            val intent = Intent(context, OfferDetailActivity::class.java)
            intent.putExtra("idUser", args.idUser)
            intent.putExtra("idOffer", args.idOffer)
            startActivity(intent)
        }

        binding.vBackBottom.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun cleanTokenAndRedirectToLogin() {
        tokenViewModel.cleanTokenPreferences()
        myUtils.redirectToLogin(requireContext())
    }
}