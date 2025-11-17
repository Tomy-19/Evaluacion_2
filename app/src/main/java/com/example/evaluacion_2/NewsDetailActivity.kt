package com.example.evaluacion_2

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        tvTitle = findViewById(R.id.tvDetailTitle)
        tvContent = findViewById(R.id.tvDetailContent)

        // Flecha compacta de volver
        findViewById<ImageButton>(R.id.btnBackToList).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val id = intent.getStringExtra("news_id")
        if (id.isNullOrEmpty()) {
            finish()
            return
        }

        val dbRef = FirebaseDatabase.getInstance()
            .getReference("news")
            .child(id)

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val title = snapshot.child("title").getValue(String::class.java) ?: "Noticia"
                val content = snapshot.child("content").getValue(String::class.java) ?: ""

                tvTitle.text = title
                tvContent.text = content
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@NewsDetailActivity, "Error al cargar noticia", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }
}
