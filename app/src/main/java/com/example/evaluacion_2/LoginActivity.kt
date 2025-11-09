package com.example.evaluacion_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.etEmail)
        val pass = findViewById<EditText>(R.id.etPass)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val emailOk = !email.text.isNullOrBlank()
            val passOk = !pass.text.isNullOrBlank()

            if (emailOk && passOk) {
                val intent = Intent(this@LoginActivity, NewsListActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Completa correo y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnToRegister).setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnToRecover).setOnClickListener {
            val intent = Intent(this@LoginActivity, RecoverActivity::class.java)
            startActivity(intent)
        }
    }
}
