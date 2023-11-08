package com.aprexi.praxis.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.aprexi.praxis.myapplication.R

class ProfessionalProyectsDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professional_proyects_detail)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_detail_professional_proyects) as NavHostFragment
        val navController = navHostFragment.findNavController()

        // Pasar argumentos al destino inicial
        // Verifica si se pasan los parámetros a través del Intent
        val idProfessionalProyects = intent.getIntExtra("idProfessionalProyects", 0)
        //val idOffer = intent.getIntExtra("idOffer", 0)

        // Configura los argumentos para el fragmento
        val args = Bundle()
        args.putInt("idProfessionalProyects", idProfessionalProyects)
        //args.putInt("idOffer", idOffer)

        navController.setGraph(R.navigation.professional_proyects_detail_navigation, args)
    }

    override fun onStop() {
        super.onStop()
        if (!isChangingConfigurations) {
            // La actividad se está volviendo invisible y no debido a un cambio de configuración.
            finish()
        }
    }
}