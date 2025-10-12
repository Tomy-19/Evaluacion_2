package com.example.evaluacion_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RecoverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover)

        val email = findViewById<EditText>(R.id.etEmail)

        findViewById<Button>(R.id.btnRecover).setOnClickListener {
            // Simula envío de correo
            AlertDialog.Builder(this@RecoverActivity)
                .setMessage(getString(R.string.dialog_recover_success))
                .setPositiveButton(R.string.dialog_ok, null)
                .show()
        }

        findViewById<Button>(R.id.btnBackLogin).setOnClickListener {
            startActivity(Intent(this@RecoverActivity, LoginActivity::class.java))
            finish()
        }
    }
}
