package com.example.evaluacion_2

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluacion_2.news.News
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class NewsListActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var rv: RecyclerView
    private lateinit var adapter: NewsAdapter
    private val newsList = mutableListOf<News>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        // RecyclerView
        rv = findViewById(R.id.rvNews)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(newsList) { news ->
            val i = Intent(this, NewsDetailActivity::class.java)
            i.putExtra("news_id", news.id)
            startActivity(i)
        }
        rv.adapter = adapter

        // Firebase /news
        dbRef = FirebaseDatabase.getInstance().getReference("news")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                newsList.clear()
                for (child in snapshot.children) {
                    val item = child.getValue(News::class.java)
                    if (item != null) {
                        newsList.add(item)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Podrías mostrar un Toast si quieres
            }
        })

        // Texto Cerrar sesión (con confirmación)
        val btnLogout = findViewById<TextView>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("Estás a punto de cerrar sesión. ¿Estás seguro?")
                .setPositiveButton("Sí") { _: DialogInterface, _: Int ->
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        // Botón "+" para crear noticia
        val btnAddNews = findViewById<FloatingActionButton>(R.id.btnAddNews)
        btnAddNews.setOnClickListener {
            startActivity(Intent(this, CreateNewsActivity::class.java))
        }
    }
}
