package com.mobiluygulamagelistirme.myapplication.feature

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mobiluygulamagelistirme.myapplication.common.component.ActiveListCart
import com.mobiluygulamagelistirme.myapplication.common.component.CompactHistoryCart
import com.mobiluygulamagelistirme.myapplication.common.component.formatDate
import com.mobiluygulamagelistirme.myapplication.feature.viewmodel.HomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onNavigateListScreen: (Int?, Boolean) -> Unit,
    viewModel: HomeViewModel
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Aktif Listeler", "Geçmiş")

    val activeLists = viewModel.activeLists
    val pastLists = viewModel.pastLists
    val searchQuery = viewModel.searchQuery
    val activeSortType = viewModel.activeSortType
    val isAscending = viewModel.isAscending

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val columnCount = if (isLandscape) 2 else 1

    Scaffold(
        floatingActionButton = {
            if (selectedTabIndex == 0) {
                FloatingActionButton(
                    onClick = { onNavigateListScreen(null, false) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Yeni Liste Ekle")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // SEKMELER (SABİT KALACAK)
            // Kullanıcı sekmelerin kaybolmasını istemez genelde, o yüzden bunu liste dışında bıraktım.
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            val listsToShow = if (selectedTabIndex == 0) activeLists else pastLists

            if (listsToShow.isEmpty() && searchQuery.isEmpty()) {
                // Arama yapılmıyorsa ve liste boşsa, arama çubuğunu yine de gösterelim
                // Ancak liste boşsa sadece ortada yazı göstermek yerine arama çubuğunu da içeren bir yapı kurabiliriz.
                // Basitlik adına boş durumda sadece yazıyı gösteriyorum, ama arama çubuğu da eklenirse daha iyi olur.
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Liste Bulunamadı", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                }
            } else {
                // --- LİSTE VE ARAMA ALANI (HEPSİ KAYDIRILABİLİR) ---
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnCount),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    // 1. ELEMAN: ARAMA VE SIRALAMA ALANI (HEADER)
                    // span = { GridItemSpan(columnCount) } diyerek tüm genişliği kaplamasını sağlıyoruz.
                    item(span = { GridItemSpan(columnCount) }) {
                        Column {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { viewModel.onSearchQueryChange(it) },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Liste Ara") },
                                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Ara") },
                                trailingIcon = {
                                    if (searchQuery.isNotEmpty()) {
                                        IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                                            Icon(Icons.Filled.Clear, contentDescription = "Temizle")
                                        }
                                    }
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Sıralama:",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    SortChip("Tarih", activeSortType == SortType.DATE, isAscending) { viewModel.onSortChange(SortType.DATE) }
                                    SortChip("Fiyat", activeSortType == SortType.PRICE, isAscending) { viewModel.onSortChange(SortType.PRICE) }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()
                        }
                    }

                    // 2. ELEMANLAR: LİSTELER
                    items(listsToShow, key = { it.id }) { shoppingList ->
                        val total = shoppingList.items.sumOf { it.Price }

                        Column(modifier = Modifier.animateItemPlacement()) {
                            if (selectedTabIndex == 0) {
                                ActiveListCart(
                                    listName = shoppingList.name,
                                    date = formatDate(shoppingList.date, "dd.MM.yyyy"),
                                    itemList = shoppingList.items,
                                    onItemCheckedChange = { item, isChecked ->
                                        viewModel.isItemChecked(shoppingList, item, isChecked)
                                    },
                                    onEditClick = { onNavigateListScreen(shoppingList.id, false) },
                                    onDeleteClick = { viewModel.deleteList(shoppingList.id) }
                                )
                            } else {
                                CompactHistoryCart(
                                    listName = shoppingList.name,
                                    date = formatDate(shoppingList.date, "dd.MM.yyyy"),
                                    totalPrice = total,
                                    itemCount = shoppingList.items.size,
                                    onDeleteClick = { viewModel.deleteList(shoppingList.id) },
                                    onRecreateClick = { onNavigateListScreen(shoppingList.id, true) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SortChip(text: String, isActive: Boolean, isAscending: Boolean, onClick: () -> Unit) {
    val icon = if (isActive) {
        if (isAscending) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward
    } else null
    ElevatedFilterChip(
        selected = isActive,
        onClick = onClick,
        label = { Text(text = text) },
        leadingIcon = if (isActive) { { Icon(imageVector = icon!!, contentDescription = null, modifier = Modifier.size(18.dp)) } } else null,
        colors = FilterChipDefaults.elevatedFilterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = Color.White,
            selectedLeadingIconColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp)
    )
}