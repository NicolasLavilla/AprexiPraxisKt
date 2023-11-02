package com.aprexi.praxis.myapplication.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentDetailRequestOfferBinding
import com.aprexi.praxis.myapplication.model.ListDetailRequestOffer
import com.aprexi.praxis.myapplication.model.Offer
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.presentation.OfferDetailActivity
import com.aprexi.praxis.myapplication.presentation.SplashActivity
import com.aprexi.praxis.myapplication.presentation.adpter.DetailRequestOfferListAdapter
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailRequestOfferListState
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailRequestOfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DetailRequestOfferFragment: Fragment() {


    private val binding: FragmentDetailRequestOfferBinding by lazy {
        FragmentDetailRequestOfferBinding.inflate(layoutInflater)
    }

    private val offerListAdapter = DetailRequestOfferListAdapter()
    private val args: DetailRequestOfferFragmentArgs by navArgs()
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val detailRequestOfferViewModel: DetailRequestOfferViewModel by activityViewModel()
    private val offerViewModel: OfferViewModel by activityViewModel()
    private var loginToken: String = ""
    private var succesToken: Boolean = false
    //private var idUser: Int = 0

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
            showErrorDialog(e.toString())
        }
    }

    private fun initViewModel() {
        offerViewModel.getOfferDetailLiveData().observe(viewLifecycleOwner, this::OfferDetailState)
        detailRequestOfferViewModel.getDetailRequestOfferListLiveData().observe(viewLifecycleOwner, this::DetailRequestOfferListState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }


    private fun OfferDetailState(state: OfferDetailState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleOfferDetailSuccess(state.result) //initUI()
            is ResourceState.SuccessFaild -> handleSuccessResponse()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> { }
        }
    }

    private fun handleOfferDetailSuccess(offer: Offer) {
        showProgressBar(false)
        initUI(offer)
    }

    private fun DetailRequestOfferListState(state: DetailRequestOfferListState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleDetailRequestOfferListSuccess(state.result)
            is ResourceState.SuccessFaild -> handleSuccessResponse()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> { }
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

    private fun handleDetailRequestOfferListSuccess(result: ListDetailRequestOffer) {
        showProgressBar(false)
        offerListAdapter.submitList(result.detailRequestOffer)
    }

    private fun handleSuccessResponse() {
        showProgressBar(false)
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

    private fun showProgressBar(show: Boolean) {
        binding.pbDetailRequestOfferFragment.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun cleanTokenAndRedirectToLogin() {
        tokenViewModel.cleanTokenPreferences()
        redirectToLogin()
    }

    private fun redirectToLogin() {
        val intent = Intent(requireContext(), SplashActivity::class.java)
        startActivity(intent)
    }

    private fun calculateElapsedTime(datePublication: String): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateCreated = dateFormat.parse(datePublication)

        val timeDifferenceMillis = currentDate.time - dateCreated.time
        val seconds = timeDifferenceMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            days > 0 -> "Hace $days días"
            hours > 0 -> "Hace $hours horas"
            minutes > 0 -> "Hace $minutes minutos"
            else -> "Hace $seconds segundos"
        }
    }

    private fun showErrorDialog(error: String, action: (() -> Unit)? = null) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error)
            .setMessage(error)
            .setPositiveButton(R.string.action_ok) { _, _ -> action?.invoke() }
            .show()
    }

}