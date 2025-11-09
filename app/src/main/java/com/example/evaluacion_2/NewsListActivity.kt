package com.example.evaluacion_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluacion_2.news.NewsRepository

class NewsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        val rv = findViewById<RecyclerView>(R.id.rvNews)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // Limpia la pila de actividades para no volver con "back"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = NewsAdapter(NewsRepository.items) { news ->
            val i = Intent(this, NewsDetailActivity::class.java)
            i.putExtra("news_id", news.id)
            startActivity(i)
        }
    }
}
