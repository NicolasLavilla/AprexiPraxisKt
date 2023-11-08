package com.aprexi.praxis.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.aprexi.praxis.myapplication.R

class UserDataDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data_detail)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_detail_user) as NavHostFragment
        val navController = navHostFragment.findNavController()

        // Pasar argumentos al destino inicial
        // Verifica si se pasan los parámetros a través del Intent
        val idUser = intent.getIntExtra("idUser", 0)

        // Configura los argumentos para el fragmento
        val args = Bundle()
        args.putInt("idUser", idUser)

        navController.setGraph(R.navigation.user_detail_navigation, args)
    }

    override fun onStop() {
        super.onStop()
        if (!isChangingConfigurations) {
            // La actividad se está volviendo invisible y no debido a un cambio de configuración.
            finish()
        }
    }
}
