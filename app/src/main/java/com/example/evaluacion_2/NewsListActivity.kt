package com.example.evaluacion_2.news

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.evaluacion_2.databinding.ActivityNewsListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.evaluacion_2.LoginActivity

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

        binding.btnLogout.setOnClickListener {
            logout()
        }

        loadNewsRealtime()
    }

    private fun loadNewsRealtime() {
        FirebaseFirestore.getInstance()
            .collection("news")
            .whereIn("status", listOf("aprobado", "Aprobado", "APROBADO"))
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                if (snapshot != null) {
                    newsList.clear()
                    for (doc in snapshot.documents) {
                        val item = doc.toObject(News::class.java)
                        item?.id = doc.id
                        if (item != null) newsList.add(item)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun openDetail(news: News) {
        val intent = Intent(this, NewsDetailActivity::class.java)
        intent.putExtra("newsId", news.id)
        startActivity(intent)
    }
}
