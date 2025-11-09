package com.example.evaluacion_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RecoverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover)

        val email = findViewById<EditText>(R.id.etEmailRecover)
        val btnRecover = findViewById<Button>(R.id.btnRecover)
        val btnBack = findViewById<Button>(R.id.btnBackLogin)

        // Botón enviar recuperación
        btnRecover.setOnClickListener {
            val emailText = email.text.toString()
            if (emailText.isNotEmpty()) {
                Toast.makeText(this, "Se envió un enlace a $emailText", Toast.LENGTH_SHORT).show()
                // Opcional: redirigir al login después de 2s
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Ingresa un correo válido", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón volver al login
        btnBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
