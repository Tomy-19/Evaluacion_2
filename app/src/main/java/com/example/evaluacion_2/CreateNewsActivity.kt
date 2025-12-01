package com.example.evaluacion_2.news

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.evaluacion_2.databinding.ActivityCreateNewsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class CreateNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNewsBinding

    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackNewsList.setOnClickListener { finish() }

        binding.btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        binding.btnSave.setOnClickListener {
            saveNewsHandler()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            Glide.with(this).load(selectedImageUri).into(binding.ivNewsImagePreview)
        }
    }

    private fun saveNewsHandler() {
        val title = binding.etTitle.text.toString()
        val subtitle = binding.etSubtitle.text.toString()
        val content = binding.etContent.text.toString()
        val imageUrlTyped = binding.etImageUrl.text.toString().trim()

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Título y contenido son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        if (imageUrlTyped.isNotEmpty()) {
            saveNews(imageUrlTyped)
            return
        }

        if (selectedImageUri != null) {
            uploadImageToStorage(selectedImageUri!!)
        } else {
            saveNews("")
        }
    }

    private fun uploadImageToStorage(uri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference
        val fileRef = storageRef.child("news_images/${System.currentTimeMillis()}.jpg")

        fileRef.putFile(uri)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { url ->
                    saveNews(url.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error subiendo imagen", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveNews(imageUrl: String) {
        val db = FirebaseFirestore.getInstance()
        val id = db.collection("news").document().id

        val news = News(
            id = id,
            title = binding.etTitle.text.toString(),
            subtitle = binding.etSubtitle.text.toString(),
            content = binding.etContent.text.toString(),
            authorId = "admin",
            authorName = "Administrador",
            imageUrl = imageUrl
        )

        db.collection("news").document(id)
            .set(news)
            .addOnSuccessListener {
                Toast.makeText(this, "Noticia creada", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al guardar la noticia", Toast.LENGTH_SHORT).show()
            }
    }
}
