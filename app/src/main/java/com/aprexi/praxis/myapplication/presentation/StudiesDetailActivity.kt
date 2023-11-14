package com.aprexi.praxis.myapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        val idStudiesUser = intent.getIntExtra("idStudiesUser", 0)
        val idNameStudies = intent.getIntExtra("idNameStudies", 0)
        val typeStudies = intent.getIntExtra("typeStudies", 0)
        val professionalFamilies = intent.getIntExtra("professionalFamilies", 0)
        val idSchool = intent.getIntExtra("idSchool", 0)
        val startYear = intent.getStringExtra("startYear")
        val endYear = intent.getStringExtra("endYear") ?: ""
        val idFragment = intent.getIntExtra("idFragment", 0)

        // Configura los argumentos para el fragmento
        val args = Bundle()
        args.putInt("idStudies", idStudiesUser)
        args.putInt("idNameStudies", idNameStudies)
        args.putInt("typeStudies", typeStudies)
        args.putInt("professionalFamilies", professionalFamilies)
        args.putInt("idSchool", idSchool)
        args.putString("startYear", startYear)
        args.putString("endYear", endYear)
        args.putInt("idFragment", idFragment)

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