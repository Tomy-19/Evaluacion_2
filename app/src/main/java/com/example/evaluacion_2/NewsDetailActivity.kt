package com.example.evaluacion_2

import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.evaluacion_2.news.NewsRepository

class NewsDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val id = intent.getIntExtra("news_id", -1)
        val news = NewsRepository.findById(id)

        findViewById<TextView>(R.id.tvDetailTitle).text = news?.title ?: "Noticia"
        findViewById<TextView>(R.id.tvDetailContent).text = news?.content ?: ""
        findViewById<Button>(R.id.btnBackToList).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
