package com.mobiluygulamagelistirme.myapplication.common.model

import java.util.Date

data class ShoppingList(
    val id: Int,
    val name: String,
    val date: Date,
    val items: List<CartItem>,
    var isArchived: Boolean = false // YENİ ALAN (Varsayılan false)
)