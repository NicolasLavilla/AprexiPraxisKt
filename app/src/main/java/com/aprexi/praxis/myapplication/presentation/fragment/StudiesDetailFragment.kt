package com.aprexi.praxis.myapplication.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.aprexi.praxis.myapplication.databinding.FragmentStudiesDetailBinding
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.StudiesUser
import com.aprexi.praxis.myapplication.presentation.SplashActivity
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailStudiesViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.StudiesUserState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StudiesDetailFragment: Fragment() {

    private val binding: FragmentStudiesDetailBinding by lazy {
        FragmentStudiesDetailBinding.inflate(layoutInflater)
    }

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

    private val args: StudiesDetailFragmentArgs by navArgs()
    private val studiesViewModel: DetailStudiesViewModel by activityViewModel()
    private val tokenViewModel: TokenViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()

    }

    private fun handleAuthentication() {
        try {
            if (succesToken) {
                tokenViewModel.fetchCheckToken(loginToken)
                studiesViewModel.getStudiesUser(
                    idUser = idUser,
                    idStudiesUser = args.idStudies,
                    token = loginToken
                )
            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            showErrorDialog(e.toString())
        }
    }

    private fun getTokenLoginPreference(){
        lifecycleScope.launch {
            val uni = tokenViewModel.fetchLoginTokenPreferences()
            loginToken = uni.token
            succesToken = uni.success
            idUser = uni.idUser
        }
    }

    private fun initViewModel() {
        studiesViewModel.studiesUserLiveData().observe(viewLifecycleOwner, this::handleStudiesUserState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> showProgressBar(false)
            is ResourceState.Error -> showErrorDialog(state.error) { cleanTokenAndRedirectToLogin() }
            else -> { }
        }
    }

    private fun handleStudiesUserState(state: StudiesUserState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> { }
        }
    }

    private fun handleSuccessOfferDetail(studies: StudiesUser) {
        showProgressBar(false)
        initUI(studies)
    }

    private fun initUI(studies: StudiesUser) {

    }

    private fun handleSuccessFailed() {
        showProgressBar(false)
        cleanTokenAndRedirectToLogin()
    }

    private fun showProgressBar(show: Boolean) {
        binding.pbStudiesDetail.visibility = if (show) View.VISIBLE else View.GONE
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
            .setTitle(com.aprexi.praxis.myapplication.R.string.error)
            .setMessage(error)
            .setPositiveButton(com.aprexi.praxis.myapplication.R.string.action_ok) { _, _ -> action?.invoke() }
            .show()
    }

}




/*val names = arrayOf("Nombre 1", "Nombre 2", "Nombre 3", "Nombre 4", "Nombre 5")


        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, names)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = binding.actvTypeStudiesDetailFragment
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = names[position]
                // Realiza alguna acción con el elemento seleccionado
                Toast.makeText(requireContext(), "Seleccionado: $selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada
            }
        }*/