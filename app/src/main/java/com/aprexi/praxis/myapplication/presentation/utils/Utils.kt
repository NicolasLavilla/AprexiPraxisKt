package com.aprexi.praxis.myapplication.presentation.utils

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.presentation.SplashActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Utils {

    val GENDER_MALE = 1
    val GENDER_FEMALE = 2

    fun changeDateFormatEU(fecha: String): String {
        // Formato actual de la fecha
        val formatoActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            // Convertir la fecha actual al formato deseado
            val date = formatoActual.parse(fecha)
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace() // Manejar cualquier excepción que pueda ocurrir al cambiar el formato de la fecha
        }

        return "" // Devolver una cadena vacía si hay algún error
    }

    fun changeDateFormatEUR(fecha: String): String {
        // Formato actual de la fecha
        val formatoActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            // Convertir la fecha actual al formato deseado
            val date = formatoActual.parse(fecha)
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return format.format(date)
        } catch (e: Exception) {
            e.printStackTrace() // Manejar cualquier excepción que pueda ocurrir al cambiar el formato de la fecha
        }

        return "" // Devolver una cadena vacía si hay algún error
    }

    fun showDatePicker(context: Context, listener: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = sdf.format(selectedDate.time)

                listener.invoke(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    fun capitalizeFirstLetter(text: String): String {
        return text.trim()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }


    fun validateAge(context: Context, birthday: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaActual = Calendar.getInstance().time

        try {
            val fechaNac: Date = dateFormat.parse(birthday)

            val calendarNac = Calendar.getInstance()
            calendarNac.time = fechaNac

            val anios = Calendar.getInstance().get(Calendar.YEAR) - calendarNac.get(Calendar.YEAR)

            if (calendarNac.get(Calendar.MONTH) > Calendar.getInstance().get(Calendar.MONTH) ||
                (calendarNac.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) &&
                        calendarNac.get(Calendar.DAY_OF_MONTH) > Calendar.getInstance()
                    .get(Calendar.DAY_OF_MONTH))
            ) {
                return anios >= 16
            }

            if (anios > 16) {
                return true
            } else {
                showToast(context, "No puedes registrarte por ser menor de edad")
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun verificationNie(nie: String, component: EditText?): Boolean {
        if (nie.isNotBlank() && nie.isNotEmpty()) {
            val regex = Regex("^[XYZ][0-9]{7}[A-Z]$")
            return if (regex.matches(nie)) {
                true
            } else {
                if (component != null) {
                    component.background =
                        R.drawable.spinner_background_error.toDrawable()
                }
                false
            }
        }
        return true
    }

    fun verificationMobile(numero: Int, component: EditText?): Boolean {
        val regex = Regex("^\\+?([0-9]){9,14}\$")

        return if (regex.matches(numero.toString())) {
            true
        } else {
            if (component != null) {
                component.background = R.drawable.spinner_background_error.toDrawable()
            }
            false
        }
    }

    fun verificationPassport(passport: String, component: EditText?): Boolean {
        if (passport.isNotBlank() && passport.isNotEmpty()) {
            val regex = Regex("^[A-Za-z0-9]{6,20}$")
            return if (regex.matches(passport)) {
                true
            } else {
                if (component != null) {
                    component.background =
                        R.drawable.spinner_background_error.toDrawable()
                }
                false
            }
        }
        return true
    }

    fun verificationDni(dni: String, component: EditText?): Boolean {

        if (dni.isNotBlank() && dni.isNotEmpty()) {
            val regex = Regex("^[0-9]{8}[A-Z]$")
            return if (regex.matches(dni)) {
                true
            } else {
                if (component != null) {
                    component.background = R.drawable.spinner_background_error.toDrawable()
                }
                false
            }
        }
        return true
    }

    fun verificationEmail(email: String, component: EditText?): Boolean {
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            true
        } else {
            if (component != null) {
                component.background =
                    R.drawable.spinner_background_error.toDrawable()
            }
            false
        }
    }

    fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(password.toByteArray()).joinToString("") { "%02x".format(it) }
    }

    fun verificationSecurityPassword(context: Context, contrasena: String): Boolean {
        val longitud = contrasena.length

        // Comprueba la longitud de la contraseña
        if (longitud < 8) {
            showToast(
                context,
                "La contraseña es demasiado corta. Debe tener al menos 8 caracteres."
            )
            return false
        }

        // Comprueba si la contraseña contiene letras mayúsculas y minúsculas
        if (!contrasena.any { it.isUpperCase() } || !contrasena.any { it.isLowerCase() }) {
            showToast(
                context,
                "La contraseña debe contener al menos una letra mayúscula y una minúscula."
            )
            return false
        }

        // Comprueba si la contraseña contiene al menos un número
        if (!contrasena.any { it.isDigit() }) {
            showToast(context, "La contraseña debe contener al menos un número.")
            return false
        }

        return true
    }

    fun showErrorDialog(context: Context, error: String, action: (() -> Unit)? = null) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.error)
            .setMessage(error)
            .setPositiveButton(R.string.action_ok) { _, _ -> action?.invoke() }
            .show()
    }

    fun showProgressBar(show: Boolean, progressBar: ProgressBar) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun redirectToLogin(context: Context) {
        val intent = Intent(context, SplashActivity::class.java)
        context.startActivity(intent)
    }

    fun calculateElapsedTime(datePublication: String): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateCreated = dateFormat.parse(datePublication)

        val timeDifferenceMillis = currentDate.time - dateCreated.time
        val seconds = timeDifferenceMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            days > 0 -> "Hace $days días"
            hours > 0 -> "Hace $hours horas"
            minutes > 0 -> "Hace $minutes minutos"
            else -> "Hace $seconds segundos"
        }
    }

    fun transformDateFormatMMYYYY(inputDate: String): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MMM yyyy", Locale("es", "ES"))

        val date = inputDateFormat.parse(inputDate)
        val formattedDate = outputDateFormat.format(date!!)

        return formattedDate.replaceFirstChar { it.uppercase() }
    }
}