package com.aprexi.praxis.myapplication.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentSplashBinding
import com.aprexi.praxis.myapplication.model.Login
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SplashFragment : Fragment() {

    private val binding: FragmentSplashBinding by lazy {
        FragmentSplashBinding.inflate(layoutInflater)
    }

    private val tokenViewModel: TokenViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        checkLoginToken()
    }

    private fun initViewModel() {
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner) { state ->
            handleSplashState(state)
        }
    }

    private fun checkLoginToken() {
        lifecycleScope.launch {
            try {
                val loginToken: Login = tokenViewModel.fetchLoginTokenPreferences()
                if (loginToken.success) {
                    tokenViewModel.fetchCheckToken(loginToken.token)
                } else {
                    tokenViewModel.cleanTokenPreferences()
                    redirectToLoginWithDelay()
                }
            } catch (e: Exception) {
                showErrorDialog(e.toString())
            }
        }
    }

    private fun handleSplashState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Success -> {
                val tokenResponse = state.result
                initUI(tokenResponse.checkToken)
            }

            is ResourceState.Error -> {
                showErrorDialog(state.error)
            }

            else -> {}
        }
    }

    private fun initUI(token: Boolean) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (token) {
                redirectToMainActivityWithDelay()
            } else {
                redirectToLoginWithDelay()
            }
        }, 0)
    }

    private fun redirectToLoginWithDelay() {
        lifecycleScope.launch {
            delay(1500)
            findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            )
        }
    }

    private fun redirectToMainActivityWithDelay() {
        lifecycleScope.launch {
            delay(1500)
            findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToOfferListFragment()
            )
        }
    }

    private fun showErrorDialog(error: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error)
            .setMessage(error)
            .setPositiveButton(R.string.action_ok, null)
            .show()
    }
}
