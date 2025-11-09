package com.example.evaluacion_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val user = findViewById<EditText>(R.id.etUser)
        val email = findViewById<EditText>(R.id.etEmail)
        val pass = findViewById<EditText>(R.id.etPass)

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val ok = !user.text.isNullOrBlank() && !email.text.isNullOrBlank() && !pass.text.isNullOrBlank()

            if (ok) {
                val intent = Intent(this@RegisterActivity, NewsListActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnBackLogin).setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }
}
