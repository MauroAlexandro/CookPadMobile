package com.mauroalexandro.cookpadmobile.models

data class Recipe(
    val id: Int,
    val image_url: String?,
    val ingredients: List<String>,
    val published_at: String,
    val steps: List<Step>,
    val story: String,
    val title: String,
    val user: User
)