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

    var activeLists by mutableStateOf<List<ShoppingList>>(emptyList())
        private set

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

    fun deleteList(listId: Int) {
        repository.deleteList(listId)
        updateUiList()
    }

    fun isItemChecked(shoppingList: ShoppingList, item: CartItem, isChecked: Boolean) {
        repository.updateItemCheckStatus(shoppingList.id, item, isChecked)

        val updatedList = repository.getListById(shoppingList.id)

        if (updatedList != null) {
            val allCompleted = updatedList.items.all { it.isChecked }

            if (allCompleted && !updatedList.isArchived) {
                val archivedList = updatedList.copy(isArchived = true)
                repository.updateListResponse(archivedList)
            }
        }

        updateUiList()
    }

    fun updateUiList() {
        val allLists = repository.getAllLists()

        val filtered = if (searchQuery.isBlank()) {
            allLists
        } else {
            allLists.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }

        val sorted = when (activeSortType) {
            SortType.DATE -> if (isAscending) filtered.sortedBy { it.date } else filtered.sortedByDescending { it.date }
            SortType.PRICE -> {
                if (isAscending)
                    filtered.sortedBy { list -> list.items.sumOf { it.Price } }
                else
                    filtered.sortedByDescending { list -> list.items.sumOf { it.Price } }
            }
        }

        activeLists = sorted.filter { !it.isArchived }
        pastLists = sorted.filter { it.isArchived }
    }
}