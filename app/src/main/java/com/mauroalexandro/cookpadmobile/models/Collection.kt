package com.mauroalexandro.cookpadmobile.models

data class Collection(
    val description: String,
    val id: Int,
    val preview_image_urls: List<String>,
    val recipe_count: Int,
    val title: String
)