package com.example.evaluacion_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val user = findViewById<EditText>(R.id.etUser)
        val email = findViewById<EditText>(R.id.etEmail)
        val pass  = findViewById<EditText>(R.id.etPass)

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            // Simula registro exitoso
            AlertDialog.Builder(this@RegisterActivity)
                .setMessage(getString(R.string.dialog_register_success))
                .setPositiveButton(R.string.dialog_ok, null)
                .show()
        }

        findViewById<Button>(R.id.btnBackLogin).setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }
}
