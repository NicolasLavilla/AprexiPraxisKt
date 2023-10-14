package com.aprexi.praxis.myapplication.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentOfferDetailBinding
import com.aprexi.praxis.myapplication.model.DeleteFollowOfferUser
import com.aprexi.praxis.myapplication.model.FollowOfferUser
import com.aprexi.praxis.myapplication.model.Offer
import com.aprexi.praxis.myapplication.model.RequestOfferUser
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.presentation.SplashActivity
import com.aprexi.praxis.myapplication.presentation.viewmodel.DeleteFollowOfferState
import com.aprexi.praxis.myapplication.presentation.viewmodel.FollowOfferState
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.RequestOfferState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class OfferDetailFragment : Fragment() {

    private val binding: FragmentOfferDetailBinding by lazy {
        FragmentOfferDetailBinding.inflate(layoutInflater)
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
                offersViewModel.fetchOffer(
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
        offersViewModel.getOfferDetailLiveData().observe(viewLifecycleOwner, this::handleOfferDetailState)
        offersViewModel.setRequestOfferLiveData().observe(viewLifecycleOwner, this::handleRequestOfferDetailState)
        offersViewModel.followOfferLiveData().observe(viewLifecycleOwner, this::handleFollowOfferDetailState)
        offersViewModel.deleteFollowOfferLiveData().observe(viewLifecycleOwner, this::handleDeleteFollowOfferDetailState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> showProgressBar(false)
            is ResourceState.Error -> showErrorDialog(state.error) { redirectToLogin() }
            else -> { }
        }
    }

    private fun handleDeleteFollowOfferDetailState(state: DeleteFollowOfferState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessDeleteFollowOffer(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> { }
        }
    }

    private fun handleFollowOfferDetailState(state: FollowOfferState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessFollowOffer(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> { }
        }
    }

    private fun handleRequestOfferDetailState(state: RequestOfferState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessRequestOffer(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> { }
        }
    }

    private fun handleOfferDetailState(state: OfferDetailState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> { }
        }
    }

    private fun handleSuccessFollowOffer(result: FollowOfferUser) {
        showProgressBar(false)

        if(result.success){
            followOfferXml()

        }else if (!result.success){
            Toast.makeText(this.context, "No se ha podido guardar!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessDeleteFollowOffer(result: DeleteFollowOfferUser) {
        showProgressBar(false)

        if(result.success){
            noFollowOfferXml()

        }else if (!result.success){
            Toast.makeText(this.context, "No se ha podido desguardar!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessRequestOffer(result: RequestOfferUser) {
        showProgressBar(false)

        if(result.success){
            binding.btnRequestOfferDetailFragment.isEnabled = false
            binding.btnRequestOfferDetailFragment.text = getString(R.string.is_request_offer_detail_fragment)

        }else if (!result.success){
            Toast.makeText(this.context, "No te has podido inscribir!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessOfferDetail(offer: Offer) {
        showProgressBar(false)
        initUI(offer)
    }

    private fun handleSuccessFailed() {
        showProgressBar(false)
        cleanTokenAndRedirectToLogin()
    }

    private fun initUI(offer: Offer) {
        val appBarLayout = binding.ablOfferDetailFragment
        val linearLayoutCompact = binding.llcToolbarOfferDetailFragment
        val toolbarDetailFragment = binding.tbOfferDetailFragment

        binding.tvTitleOfferDetailFragment.text = offer.offerTitle
        binding.tvMunicipalityOfferDetailFragment.text = offer.nameMunicipality
        binding.tvSalaryOfferDetailFragment.text = offer.salary.toString()
        binding.tvTimeOfferDetailFragment.text = calculateElapsedTime(offer.datePublication)
        binding.tvVacantOfferDetailFragment.text = offer.numVacancies.toString() + " inscritos"
        binding.tvInscriptionsOfferDetailFragment.text = offer.numRegistered.toString() + " vacantes"
        binding.tvModalityOfferDetailFragment.text = offer.nameModality
        binding.tvStudiesMiniumOfferDetailFragment.text = offer.nameTypeStudies
        binding.tvExperienceMiniumOfferDetailFragment.text = offer.minExperience.toString()
        binding.tvTextRequirementsMiniumOfferDetailFragment.text = offer.minRequirements
        binding.tvTextDescriptionOfferDetailFragment.text = offer.jobDescription
        binding.tvOfferNameCompanyDetailFragment.text = offer.nameCompany
        binding.tvText1LicenseOfferDetailFragment.text = offer.combinedLicenses
        binding.tvText1RequiredLanguagesOfferDetailFragment.text = offer.combinedLanguages

        Glide.with(requireContext())
            .load(offer.logoCompany)
            .into(binding.ivLogoTbOfferDetail)

        Glide.with(requireContext())
            .load(offer.logoCompany)
            .into(binding.ivLogoCollapsingTbOfferDetail)

        Glide.with(requireContext())
            .load(offer.imageCompany)
            .into(binding.ivDeskOfferDetail)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val isCollapsed = Math.abs(verticalOffset) == appBarLayout.totalScrollRange
            if (isCollapsed) {
                // La CollapsingToolbarLayout está completamente colapsada.
                linearLayoutCompact.visibility = View.VISIBLE
                toolbarDetailFragment.setBackgroundResource(R.drawable.ctr_bg)
                binding.ivLogoCollapsingTbOfferDetail.visibility = View.GONE
            } else {
                // La CollapsingToolbarLayout no está completamente colapsada.
                linearLayoutCompact.visibility = View.GONE
                toolbarDetailFragment.background = android.R.color.transparent.toDrawable()
                binding.ivLogoCollapsingTbOfferDetail.visibility = View.VISIBLE
            }
        })

        toolbarDetailFragment.setOnClickListener{
            findNavController().navigate(
                OfferDetailFragmentDirections.actionOfferDetailFragmentToCompanyFragment()
            )
        }

        //Boton Favorito
        if (offer.followOffer.toInt() == 1) {
            followOfferXml()
        } else {
            noFollowOfferXml()
        }

        binding.fabOfferDetailFragment.setOnClickListener {
            if (followOffer) {
                offersViewModel.followOffer(args.idUser, args.idOffer, loginToken)
            }else{
                offersViewModel.deleteFollowOffer(args.idUser, args.idOffer, loginToken)
            }
        }

        //Boton Inscribirse
        if (offer.requestOffer.toInt() == 1) {
            binding.btnRequestOfferDetailFragment.isEnabled = false
            binding.btnRequestOfferDetailFragment.text = getString(R.string.is_request_offer_detail_fragment)
        }else{
            binding.btnRequestOfferDetailFragment.isEnabled = true
            binding.btnRequestOfferDetailFragment.text = getString(R.string.btn_register_offer_detail_fragment)
        }

        binding.btnRequestOfferDetailFragment.setOnClickListener {
            if (offer.requestOffer.toInt() == 0 && it.isEnabled) {
                offersViewModel.setRequestOffer(args.idUser, args.idOffer, loginToken)
            }
        }
    }

    private fun followOfferXml(){
        followOffer = false
        binding.fabOfferDetailFragment.setImageResource(R.drawable.baseline_favorite_24)
    }

    private fun noFollowOfferXml(){
        followOffer = true
        binding.fabOfferDetailFragment.setImageResource(R.drawable.baseline_favorite_border_24)
    }

    private fun showProgressBar(show: Boolean) {
        binding.pbOfferDetail.visibility = if (show) View.VISIBLE else View.GONE
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
