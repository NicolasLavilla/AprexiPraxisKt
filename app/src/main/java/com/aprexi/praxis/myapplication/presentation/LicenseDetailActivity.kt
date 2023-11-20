package com.aprexi.praxis.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.aprexi.praxis.myapplication.R

class LicenseDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_license)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_license_detail) as NavHostFragment
        val navController = navHostFragment.findNavController()

        // Pasar argumentos al destino inicial
        // Verifica si se pasan los parámetros a través del Intent
        val idLicenseUser = intent.getIntExtra("idLicenseUser", 0)
        val idLicense = intent.getIntExtra("idLicense", 0)
        val idFragment = intent.getIntExtra("idFragment", 0)

        // Configura los argumentos para el fragmento
        val args = Bundle()
        args.putInt("idLicenseUser", idLicenseUser)
        args.putInt("idLicense", idLicense)
        args.putInt("idFragment", idFragment)

        navController.setGraph(R.navigation.license_detail_navigation, args)
    }

    override fun onStop() {
        super.onStop()
        if (!isChangingConfigurations) {
            // La actividad se está volviendo invisible y no debido a un cambio de configuración.
            finish()
        }
    }
}