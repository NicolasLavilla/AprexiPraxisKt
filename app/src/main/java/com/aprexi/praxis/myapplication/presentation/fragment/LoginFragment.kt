package com.aprexi.praxis.myapplication.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aprexi.praxis.myapplication.databinding.FragmentLoginBinding
import com.aprexi.praxis.myapplication.model.Login
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.presentation.BottomActivity
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.LoginDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.LoginViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var progressBar: ProgressBar
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val loginViewModel: LoginViewModel by activityViewModel()
    private val myUtils: Utils by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.pbLoginFragment
        initViewModel()

        binding.btnLoginLoginActivity.setOnClickListener {
            val email: String = binding.etEmailLoginActivity.text.toString()
            val password: String = binding.etPasswordLoginActivity.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.fetchLogin(email = email, password = myUtils.hashPassword(password))
            } else {
                myUtils.showToast(context = requireContext(),"No puede estar vacío!")
            }
        }

        binding.btnLoginGoogleLoginActivity.setOnClickListener {
            navigateToRegisterFragment()
        }
    }

    private fun initViewModel() {
        loginViewModel.getLoginLiveData().observe(viewLifecycleOwner) { state ->
            handleLoginState(state)
        }
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner) { state ->
            handleTokenState(state)
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> {
                myUtils.showProgressBar(true, progressBar)
            }

            is ResourceState.Success -> {
                myUtils.showProgressBar(false, progressBar)
            }

            is ResourceState.Error -> {
                myUtils.showProgressBar(false, progressBar)
                myUtils.showError( context = requireContext(),state.error) { retryLogin() }
            }

            else -> {}
        }
    }

    private fun handleLoginState(state: LoginDetailState) {
        when (state) {
            is ResourceState.Loading -> {
                myUtils.showProgressBar(true, progressBar)
            }

            is ResourceState.Success -> {
                myUtils.showProgressBar(false, progressBar)
                saveTokenPreferences(state.result)
                initUI(state.result)
            }

            is ResourceState.Error -> {
                myUtils.showProgressBar(false, progressBar)
                myUtils.showError( context = requireContext(),state.error) { retryLogin() }
            }

            else -> {}
        }
    }

    private fun initUI(result: Login) {
        if (result.success) {
            navigateToOfferListFragment()
        } else {
            myUtils.showToast(context = requireContext(),"Credenciales incorrectas")
        }
    }

    private fun saveTokenPreferences(result: Login) {
        tokenViewModel.saveTokenPreferences(result)
    }

    private fun navigateToOfferListFragment() {
        val intent = Intent(requireContext(), BottomActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToRegisterFragment() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    private fun retryLogin() {
        loginViewModel.fetchLogin(
            email = binding.etEmailLoginActivity.text.toString(),
            password = binding.etPasswordLoginActivity.text.toString()
        )
    }
}
