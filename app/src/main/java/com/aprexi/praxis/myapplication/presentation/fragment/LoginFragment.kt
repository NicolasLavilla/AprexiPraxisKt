package com.aprexi.praxis.myapplication.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentLoginBinding
import com.aprexi.praxis.myapplication.model.Login
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.presentation.BottomActivity
import com.aprexi.praxis.myapplication.presentation.viewmodel.LoginDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.LoginViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.security.MessageDigest

class LoginFragment : Fragment() {

    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val loginViewModel: LoginViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        binding.btnLoginLoginActivity.setOnClickListener {
            val email: String = binding.etEmailLoginActivity.text.toString()
            val password: String = binding.etPasswordLoginActivity.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val hashedPassword = hashPassword(password)
                loginViewModel.fetchLogin(email = email, password = hashedPassword)
            } else {
                showToast("No puede estar vacío!")
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
                showProgressBar(true)
            }

            is ResourceState.Success -> {
                showProgressBar(false)
            }

            is ResourceState.Error -> {
                showProgressBar(false)
                showErrorDialog(state.error) { retryLogin() }
            }

            else -> {}
        }
    }

    private fun handleLoginState(state: LoginDetailState) {
        when (state) {
            is ResourceState.Loading -> {
                showProgressBar(true)
            }

            is ResourceState.Success -> {
                showProgressBar(false)
                saveTokenPreferences(state.result)
                initUI(state.result)
            }

            is ResourceState.Error -> {
                showProgressBar(false)
                showErrorDialog(state.error) { retryLogin() }
            }

            else -> {}
        }
    }

    private fun initUI(result: Login) {
        if (result.success) {
            navigateToOfferListFragment()
        } else {
            showToast("Credenciales incorrectas")
        }
    }

    private fun saveTokenPreferences(result: Login) {
        tokenViewModel.saveTokenPreferences(result)
    }

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(password.toByteArray()).joinToString("") { "%02x".format(it) }
    }

    private fun navigateToOfferListFragment() {
        //findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToOfferListFragment())
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

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressBar(show: Boolean) {
        binding.pbLoginFragment.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showErrorDialog(error: String, onRetry: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.error)
            .setMessage(error)
            .setPositiveButton(R.string.action_ok, null)
            .setNegativeButton(R.string.action_retry) { _, _ ->
                onRetry()
            }
            .show()
    }
}
