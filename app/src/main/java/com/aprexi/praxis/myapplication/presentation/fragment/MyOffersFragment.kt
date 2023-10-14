package com.aprexi.praxis.myapplication.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentMyOffersBinding
import com.aprexi.praxis.myapplication.databinding.FragmentOfferListBinding
import com.aprexi.praxis.myapplication.presentation.adpter.OfferListAdapter
import com.aprexi.praxis.myapplication.presentation.viewmodel.OfferViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MyOffersFragment: Fragment() {

    private val binding: FragmentMyOffersBinding by lazy {
        FragmentMyOffersBinding.inflate(layoutInflater)
    }

    /*private val user: Int = 4

    private lateinit var navController: NavController
    private val offerListAdapter = OfferListAdapter()
    private val offerViewModel: OfferViewModel by activityViewModel()*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val bottomNavigationView = view.findViewById<BottomNavigationView>(binding.bottomNavMyOffers.id)
        val bottomNavigationView = binding.bottomNavMyOffers
        val navController = findNavController()

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navOfferListFragment,
                R.id.navMyOffersFragment,
                R.id.navCurriculumFragment
            )
        )

        bottomNavigationView.setupWithNavController(navController)


        /*initViewModel()
        initUI()

        offerViewModel.fetchOfferList(idUser = user) //Hay que buscar el id del usuario con sharedPreference*/
    }
}