package com.aprexi.praxis.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.aprexi.praxis.myapplication.R

class ExperienceJobsUserDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experience_jonb_user_detail)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_detail_experience_job) as NavHostFragment
        val navController = navHostFragment.findNavController()

        // Pasar argumentos al destino inicial
        // Verifica si se pasan los parámetros a través del Intent
        val idExperienceJob = intent.getIntExtra("idExperienceJob", 0)

        // Configura los argumentos para el fragmento
        val args = Bundle()
        args.putInt("idExperienceJob", idExperienceJob)

        navController.setGraph(R.navigation.experience_job_detail_navigation, args)
    }

    override fun onStop() {
        super.onStop()
        if (!isChangingConfigurations) {
            // La actividad se está volviendo invisible y no debido a un cambio de configuración.
            finish()
        }
    }
}