package com.example.evaluacion_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RecoverActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.etEmailRecover)
        val btnRecover = findViewById<Button>(R.id.btnRecover)
        val btnBack = findViewById<ImageButton>(R.id.btnBackLogin)

        btnBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnRecover.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Ingresa un correo válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Correo enviado exitosamente", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        val msg = task.exception?.localizedMessage ?: "Error al enviar correo"
                        Toast.makeText(this, "Error al enviar correo: $msg", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
