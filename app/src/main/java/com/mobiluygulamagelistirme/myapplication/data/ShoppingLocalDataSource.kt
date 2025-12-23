package com.mobiluygulamagelistirme.myapplication.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobiluygulamagelistirme.myapplication.common.model.ShoppingList

class ShoppingLocalDataSource(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("shopping_app_db", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Veriyi Kaydet
    fun saveShoppingLists(lists: List<ShoppingList>) {
        val jsonString = gson.toJson(lists)
        sharedPreferences.edit().putString("saved_lists", jsonString).apply()
    }

    // Veriyi Oku
    fun getShoppingLists(): List<ShoppingList> {
        val jsonString = sharedPreferences.getString("saved_lists", null)
        return if (jsonString != null) {
            val type = object : TypeToken<List<ShoppingList>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            emptyList()
        }
    }
}