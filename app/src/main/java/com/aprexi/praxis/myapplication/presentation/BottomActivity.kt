package com.aprexi.praxis.myapplication.presentation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.ActivityBottonBinding
import com.aprexi.praxis.myapplication.presentation.fragment.CurriculumFragment
import com.aprexi.praxis.myapplication.presentation.fragment.MyOffersFragment
import com.aprexi.praxis.myapplication.presentation.fragment.OfferListFragment

class BottomActivity : AppCompatActivity() {

private lateinit var binding: ActivityBottonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityBottonBinding.inflate(layoutInflater)
     setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment_activity_botton, OfferListFragment()).commit()

        binding.navView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navOfferListFragment -> supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_botton, OfferListFragment()).commit()
                R.id.navMyOffersFragment -> supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_botton, MyOffersFragment()).commit()
                R.id.navCurriculumFragment -> supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_botton, CurriculumFragment()).commit()
            }
            true
        }


    }
}

/*  val navView: BottomNavigationView = binding.navView

       val navController = findNavController(R.id.nav_host_fragment_activity_botton)
       // Passing each menu ID as a set of Ids because each
       // menu should be considered as top level destinations.
       //val appBarConfiguration = AppBarConfiguration(setOf(
          // R.id.navOfferListFragment, R.id.navMyOffersFragment, R.id.navCurriculumFragment))
       //setupActionBarWithNavController(navController, appBarConfiguration)
       navView.setupWithNavController(navController)*/