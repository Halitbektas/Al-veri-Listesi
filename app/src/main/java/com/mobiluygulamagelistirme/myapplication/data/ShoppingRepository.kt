package com.mobiluygulamagelistirme.myapplication.data

import com.mobiluygulamagelistirme.myapplication.common.model.CartItem
import com.mobiluygulamagelistirme.myapplication.common.model.ShoppingList

class ShoppingRepository(private val dataSource: ShoppingLocalDataSource) {

    // Bellekte tutulan canlı liste
    private var _cachedLists = mutableListOf<ShoppingList>()

    init {
        // Uygulama açılınca verileri yükle
        _cachedLists.addAll(dataSource.getShoppingLists())
    }

    fun getAllLists(): List<ShoppingList> {
        return _cachedLists
    }

    fun addList(list: ShoppingList) {
        _cachedLists.add(0, list) // En başa ekle
        saveChanges()
    }

    fun updateListResponse(shoppingList: ShoppingList) {
        val index = _cachedLists.indexOfFirst { it.id == shoppingList.id }
        if (index != -1) {
            _cachedLists[index] = shoppingList
            saveChanges()
        }
    }

    // Checkbox işaretlendiğinde veriyi güncelle
    fun updateItemCheckStatus(listId: Int, item: CartItem, isChecked: Boolean) {
        val listIndex = _cachedLists.indexOfFirst { it.id == listId }
        if (listIndex != -1) {
            val currentList = _cachedLists[listIndex]
            val updatedItems = currentList.items.map {
                if (it == item) it.copy(isChecked = isChecked) else it
            }
            _cachedLists[listIndex] = currentList.copy(items = updatedItems)
            saveChanges()
        }
    }

    private fun saveChanges() {
        dataSource.saveShoppingLists(_cachedLists)
    }

    fun getListById(id: Int): ShoppingList? {
        return _cachedLists.find { it.id == id }
    }

    // Listeyi Tamamen Sil
    fun deleteList(listId: Int) {
        _cachedLists.removeAll { it.id == listId }
        saveChanges()
    }
}