package com.aprexi.praxis.myapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.ActivityBottonBinding
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class BottomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBottonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_bottom) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
    }

}
