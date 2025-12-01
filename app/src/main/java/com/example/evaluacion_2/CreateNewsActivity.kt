package com.example.evaluacion_2.news

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.evaluacion_2.databinding.ActivityCreateNewsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.firestore.FieldValue

class CreateNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNewsBinding
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE = 100
    private var currentUserName = "Usuario"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserName()

        binding.btnBackNewsList.setOnClickListener { finish() }

        binding.btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        binding.btnSave.setOnClickListener { saveNewsHandler() }
    }

    private fun loadUserName() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener {
                currentUserName = it.getString("name") ?: "Usuario"
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
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid ?: ""

        val docRef = db.collection("news").document()
        val id = docRef.id

        val news = hashMapOf(
            "id" to id,
            "title" to binding.etTitle.text.toString(),
            "subtitle" to binding.etSubtitle.text.toString(),
            "content" to binding.etContent.text.toString(),
            "authorId" to uid,
            "authorName" to currentUserName,
            "imageUrl" to imageUrl,
            "status" to "pendiente",
            "createdAt" to FieldValue.serverTimestamp()
        )
        docRef.set(news)
            .addOnSuccessListener {
                Toast.makeText(this, "Tu noticia está siendo evaluada", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al guardar la noticia", Toast.LENGTH_SHORT).show()
            }
    }

}
