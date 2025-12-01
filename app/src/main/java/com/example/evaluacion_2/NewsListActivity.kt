package com.example.evaluacion_2.news

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.evaluacion_2.databinding.ActivityNewsListBinding
import com.google.firebase.firestore.FirebaseFirestore

class NewsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsListBinding
    private val newsList = ArrayList<News>()
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NewsAdapter(newsList) { openDetail(it) }
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter

        binding.btnAddNews.setOnClickListener {
            startActivity(Intent(this, CreateNewsActivity::class.java))
        }

        loadNews()
    }

    override fun onResume() {
        super.onResume()
        loadNews()
    }

    private fun loadNews() {
        FirebaseFirestore.getInstance().collection("news")
            .orderBy("createdAt")
            .get()
            .addOnSuccessListener { snap ->
                newsList.clear()
                for (doc in snap) {
                    newsList.add(doc.toObject(News::class.java))
                }
                adapter.notifyDataSetChanged()
            }
    }

    private fun openDetail(news: News) {
        val intent = Intent(this, NewsDetailActivity::class.java)
        intent.putExtra("newsId", news.id)
        startActivity(intent)
    }
}
