package com.example.kinbo


data class ShoppingItem(
    val id : Int,
    var name: String,
    var quality: Int,
    var isEditing: Boolean = false
)
