package com.example.evaluacion_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializa el Splash Screen de Android
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.etEmail)
        val pass  = findViewById<EditText>(R.id.etPass)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            if (email.text.isNullOrBlank() || pass.text.isNullOrBlank()) {
                AlertDialog.Builder(this@LoginActivity)
                    .setMessage(getString(R.string.dialog_login_required))
                    .setPositiveButton(R.string.dialog_ok, null)
                    .show()
            } else {
                AlertDialog.Builder(this@LoginActivity)
                    .setMessage(getString(R.string.dialog_login_success))
                    .setPositiveButton(R.string.dialog_ok, null)
                    .show()
            }
        }

        findViewById<Button>(R.id.btnToRegister).setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
        findViewById<Button>(R.id.btnToRecover).setOnClickListener {
            startActivity(Intent(this@LoginActivity, RecoverActivity::class.java))
        }
    }
}
