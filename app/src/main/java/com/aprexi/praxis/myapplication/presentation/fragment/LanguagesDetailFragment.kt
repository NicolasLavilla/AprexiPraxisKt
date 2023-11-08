package com.aprexi.praxis.myapplication.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.aprexi.praxis.myapplication.databinding.FragmentLanguagesDetailBinding
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class LanguagesDetailFragment: Fragment() {

    private val binding: FragmentLanguagesDetailBinding by lazy {
        FragmentLanguagesDetailBinding.inflate(layoutInflater)
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

    private val args: LanguagesDetailFragmentArgs by navArgs()
    private val tokenViewModel: TokenViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTokenLoginPreference()
        /*initViewModel()
        handleAuthentication()*/
    }

    private fun getTokenLoginPreference(){
        lifecycleScope.launch {
            val uni = tokenViewModel.fetchLoginTokenPreferences()
            loginToken = uni.token
            succesToken = uni.success
            idUser = uni.idUser
        }
    }
}