package com.mobiluygulamagelistirme.myapplication.feature.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mobiluygulamagelistirme.myapplication.common.model.CartItem
import com.mobiluygulamagelistirme.myapplication.common.model.ShoppingList
import com.mobiluygulamagelistirme.myapplication.data.ShoppingRepository
import com.mobiluygulamagelistirme.myapplication.feature.SortType

class HomeViewModel(private val repository: ShoppingRepository) : ViewModel() {

    // Aktif Listeler
    var activeLists by mutableStateOf<List<ShoppingList>>(emptyList())
        private set

    // Geçmiş (Arşivlenmiş) Listeler
    var pastLists by mutableStateOf<List<ShoppingList>>(emptyList())
        private set

    var searchQuery by mutableStateOf("")
        private set

    var activeSortType by mutableStateOf(SortType.DATE)
        private set

    var isAscending by mutableStateOf(true)
        private set

    init {
        updateUiList()
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
        updateUiList()
    }

    fun onSortChange(type: SortType) {
        if (activeSortType == type) {
            isAscending = !isAscending
        } else {
            activeSortType = type
            isAscending = true
        }
        updateUiList()
    }

    // YENİ: Listeyi Sil
    fun deleteList(listId: Int) {
        repository.deleteList(listId)
        updateUiList()
    }

    // GÜNCELLENDİ: Checkbox Mantığı ve Otomatik Arşivleme
    fun isItemChecked(shoppingList: ShoppingList, item: CartItem, isChecked: Boolean) {
        repository.updateItemCheckStatus(shoppingList.id, item, isChecked)

        // Güncel listeyi repo'dan çekip kontrol et
        val updatedList = repository.getListById(shoppingList.id)

        if (updatedList != null) {
            // Eğer tüm ürünler işaretlendiyse (isChecked == true)
            val allCompleted = updatedList.items.all { it.isChecked }

            if (allCompleted && !updatedList.isArchived) {
                // Listeyi arşive taşı (Otomatik Geçmişe Atma)
                val archivedList = updatedList.copy(isArchived = true)
                repository.updateListResponse(archivedList)
            }
            // Opsiyonel: Eğer kullanıcı geçmiş listeden bir tik kaldırırsa tekrar aktife dönsün mü?
            // Şimdilik dönmesin, sadece geçmişe gitsin istedin.
        }

        updateUiList()
    }

    fun updateUiList() {
        val allLists = repository.getAllLists()

        // 1. Arama Filtresi
        val filtered = if (searchQuery.isBlank()) {
            allLists
        } else {
            allLists.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }

        // 2. Sıralama
        val sorted = when (activeSortType) {
            SortType.DATE -> if (isAscending) filtered.sortedBy { it.date } else filtered.sortedByDescending { it.date }
            SortType.PRICE -> {
                if (isAscending)
                    filtered.sortedBy { list -> list.items.sumOf { it.Price } }
                else
                    filtered.sortedByDescending { list -> list.items.sumOf { it.Price } }
            }
        }

        // 3. Ayrıştırma (Aktif vs Geçmiş)
        activeLists = sorted.filter { !it.isArchived }
        pastLists = sorted.filter { it.isArchived }
    }
}