package com.example.evaluacion_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.evaluacion_2.news.News
import com.google.firebase.database.FirebaseDatabase

class CreateNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_news)

        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etSubtitle = findViewById<EditText>(R.id.etSubtitle)
        val etContent = findViewById<EditText>(R.id.etContent)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnBack = findViewById<ImageButton>(R.id.btnBackNewsList)

        val dbRef = FirebaseDatabase.getInstance().getReference("news")

        // Flecha volver → simplemente cerrar esta pantalla
        btnBack.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val subtitle = etSubtitle.text.toString().trim()
            val content = etContent.text.toString().trim()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Título y contenido son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = dbRef.push().key ?: return@setOnClickListener

            val news = News(
                id = id,
                title = title,
                subtitle = subtitle,
                content = content
            )

            dbRef.child(id).setValue(news)
                .addOnSuccessListener {
                    Toast.makeText(this, "Noticia creada correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al guardar la noticia", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
