package com.aprexi.praxis.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.aprexi.praxis.myapplication.R

class LanguagesDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_languages_detail)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_languages_detail) as NavHostFragment
        val navController = navHostFragment.findNavController()

        // Pasar argumentos al destino inicial
        // Verifica si se pasan los parámetros a través del Intent
        val idLanguages = intent.getIntExtra("idLanguages", 0)

        // Configura los argumentos para el fragmento
        val args = Bundle()
        args.putInt("idLanguages", idLanguages)

        navController.setGraph(R.navigation.languages_detail_navigation, args)
    }

    override fun onStop() {
        super.onStop()
        if (!isChangingConfigurations) {
            // La actividad se está volviendo invisible y no debido a un cambio de configuración.
            finish()
        }
    }
}