package com.example.evaluacion_2.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.evaluacion_2.databinding.ActivityNewsDetailBinding
import com.google.firebase.firestore.FirebaseFirestore

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsId = intent.getStringExtra("newsId") ?: return

        binding.btnBackToList.setOnClickListener { finish() }

        loadNews(newsId)
    }

    private fun loadNews(id: String) {
        FirebaseFirestore.getInstance()
            .collection("news")
            .document(id)
            .get()
            .addOnSuccessListener { snap ->
                val news = snap.toObject(News::class.java) ?: return@addOnSuccessListener

                binding.tvDetailTitle.text = news.title
                binding.tvDetailContent.text = news.content

                Glide.with(this)
                    .load(news.imageUrl)
                    .into(binding.ivDetailImage)
            }
    }
}
