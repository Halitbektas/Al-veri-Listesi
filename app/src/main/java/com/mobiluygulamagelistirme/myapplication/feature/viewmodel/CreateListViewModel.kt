package com.mobiluygulamagelistirme.myapplication.feature.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mobiluygulamagelistirme.myapplication.common.model.CartItem
import com.mobiluygulamagelistirme.myapplication.common.model.MarketItem
import com.mobiluygulamagelistirme.myapplication.common.model.ShoppingList
import com.mobiluygulamagelistirme.myapplication.data.ShoppingRepository
import com.mobiluygulamagelistirme.myapplication.feature.samplemarketList // Sample listeye erişim
import java.util.Date
import kotlin.random.Random

class CreateListViewModel(private val repository: ShoppingRepository) : ViewModel() {


    private var initializedKey: String? = null

    var listName by mutableStateOf("Yeni Liste")
        private set

    var searchProductQuery by mutableStateOf("")
        private set

    var selectedCategory by mutableStateOf("Tümü")
        private set

    private val _cartItems = mutableStateListOf<Pair<MarketItem, Int>>()
    val cartItems: List<Pair<MarketItem, Int>> get() = _cartItems

    private var currentListId: Int? = null

    fun initialize(listId: Int, isClone: Boolean) {
        val newKey = "$listId-$isClone"

        if (initializedKey == newKey) return

        if (listId != -1) {
            if (isClone) {
                cloneList(listId)
            } else {
                loadList(listId)
            }
        } else {
            resetState()
        }

        initializedKey = newKey
    }


    private fun loadList(id: Int) {
        val list = repository.getListById(id)
        if (list != null) {
            currentListId = list.id
            listName = list.name

            _cartItems.clear()
            list.items.forEach { cartItem ->
                val originalMarketItem = samplemarketList.find { it.name == cartItem.itemName }
                if (originalMarketItem != null) {
                    _cartItems.add(originalMarketItem to cartItem.Value)
                }
            }
        }
    }

    private fun cloneList(id: Int) {
        val list = repository.getListById(id)
        if (list != null) {
            listName = list.name + " (Kopya)"
            _cartItems.clear()
            list.items.forEach { cartItem ->
                val originalMarketItem = samplemarketList.find { it.name == cartItem.itemName }
                if (originalMarketItem != null) {
                    _cartItems.add(originalMarketItem to cartItem.Value)
                }
            }
            currentListId = null
        }
    }

    fun resetState() {
        listName = "Yeni Liste"
        _cartItems.clear()
        currentListId = null
        searchProductQuery = ""
        selectedCategory = "Tümü"
    }


    fun onListNameChange(newName: String) { listName = newName }
    fun onSearchProductChange(query: String) { searchProductQuery = query }
    fun onCategoryChange(category: String) { selectedCategory = category }

    fun addToCart(item: MarketItem) {
        val index = _cartItems.indexOfFirst { it.first == item }
        if (index == -1) {
            _cartItems.add(item to 1)
        } else {
            val currentQty = _cartItems[index].second
            _cartItems[index] = item to (currentQty + 1)
        }
    }

    fun decreaseFromCart(item: MarketItem) {
        val index = _cartItems.indexOfFirst { it.first == item }
        if (index != -1) {
            val currentQty = _cartItems[index].second
            if (currentQty > 1) {
                _cartItems[index] = item to (currentQty - 1)
            } else {
                _cartItems.removeAt(index)
            }
        }
    }

    fun removeFromCart(itemPair: Pair<MarketItem, Int>) {
        _cartItems.remove(itemPair)
    }

    fun getFilteredMarketItems(allMarketItems: List<MarketItem>): List<MarketItem> {
        val categoryFiltered = if (selectedCategory == "Tümü") {
            allMarketItems
        } else {
            allMarketItems.filter { it.category == selectedCategory }
        }
        return if (searchProductQuery.isBlank()) {
            categoryFiltered
        } else {
            categoryFiltered.filter { it.name.contains(searchProductQuery, ignoreCase = true) }
        }
    }

    fun getUniqueCategories(allMarketItems: List<MarketItem>): List<String> {
        val categories = allMarketItems.map { it.category }.distinct().toMutableList()
        categories.add(0, "Tümü")
        return categories
    }

    fun saveCurrentList(): Boolean {
        if (_cartItems.isEmpty() || listName.isBlank()) return false

        val cartItemList = _cartItems.map { (item, i) ->
            CartItem(
                itemName = item.name,
                Price = item.price,
                UnitType = item.unitType,
                Value = i,
                isChecked = false
            )
        }

        val finalId = currentListId ?: Random.nextInt(1000, 99999)

        val listToSave = ShoppingList(
            id = finalId,
            name = listName,
            date = Date(),
            items = cartItemList
        )

        if (currentListId != null) {
            repository.updateListResponse(listToSave)
        } else {
            repository.addList(listToSave)
        }

        initializedKey = null
        return true
    }
}