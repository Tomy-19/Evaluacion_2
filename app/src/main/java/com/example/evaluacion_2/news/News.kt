package com.example.evaluacion_2.news

import com.google.firebase.Timestamp

data class News(
    var id: String = "",
    val title: String = "",
    val subtitle: String = "",
    val content: String = "",
    val authorId: String = "",
    val authorName: String = "",
    val imageUrl: String = "",
    val status: String = "",
    val createdAt: Timestamp? = null
)
