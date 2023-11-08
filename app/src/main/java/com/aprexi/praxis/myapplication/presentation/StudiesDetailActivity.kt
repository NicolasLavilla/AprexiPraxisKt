package com.aprexi.praxis.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.aprexi.praxis.myapplication.R

class StudiesDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studies_detail)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_detail_studies) as NavHostFragment
        val navController = navHostFragment.findNavController()

        // Pasar argumentos al destino inicial
        // Verifica si se pasan los parámetros a través del Intent
        val idStudies = intent.getIntExtra("idStudies", 0)

        // Configura los argumentos para el fragmento
        val args = Bundle()
        args.putInt("idStudies", idStudies)

        navController.setGraph(R.navigation.studies_detail_navigation, args)
    }

    override fun onStop() {
        super.onStop()
        if (!isChangingConfigurations) {
            // La actividad se está volviendo invisible y no debido a un cambio de configuración.
            finish()
        }
    }
}