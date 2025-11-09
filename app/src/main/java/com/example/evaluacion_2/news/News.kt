package com.example.evaluacion_2.news

data class News(
    val id: Int,
    val title: String,
    val subtitle: String,
    val content: String
)

object NewsRepository {
    val items = listOf(
        News(1, "Android 15 Preview", "Nuevas APIs y mejoras",
            "Android 15 introduce optimizaciones de rendimiento, mejoras de privacidad y nuevas APIs para multimedia y foldables..."),
        News(2, "Material 3 actualiza componentes", "Botones, Cards y más",
            "La librería Material 3 recibe mejoras en accesibilidad, nuevos tokens y estilos más coherentes con el diseño actual..."),
        News(3, "Kotlin 2.x", "Novedades de lenguaje",
            "Kotlin continúa evolucionando con mejoras de rendimiento del compilador, nuevas funciones del lenguaje y tooling en Android Studio...")
    )
    fun findById(id: Int) = items.firstOrNull { it.id == id }
}
