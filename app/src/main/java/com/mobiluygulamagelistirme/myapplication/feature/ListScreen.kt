package com.mobiluygulamagelistirme.myapplication.feature

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobiluygulamagelistirme.myapplication.common.component.MarketItemCart
import com.mobiluygulamagelistirme.myapplication.common.model.MarketItem
import com.mobiluygulamagelistirme.myapplication.feature.viewmodel.CreateListViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListScreen(
    marketItemList: List<MarketItem>,
    onNavigateHomeScreen: () -> Unit,
    viewModel: CreateListViewModel = viewModel(),
) {
    val sepetList = viewModel.cartItems
    val listeAdi = viewModel.listName
    val aramaMetni = viewModel.searchProductQuery
    val selectedCategory = viewModel.selectedCategory

    val filtrelenmisUrunler = viewModel.getFilteredMarketItems(marketItemList)
    val categories = remember(marketItemList) { viewModel.getUniqueCategories(marketItemList) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val gridCellsCount = if (isLandscape) 4 else 2

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridCellsCount),
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item(span = { GridItemSpan(gridCellsCount) }) {
            TextField(
                value = listeAdi,
                onValueChange = { viewModel.onListNameChange(it) },
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                placeholder = { Text("Liste Adı Giriniz...", fontSize = 24.sp, color = Color.Gray) },
                singleLine = true,
                trailingIcon = { Icon(Icons.Default.Edit, contentDescription = null, tint = Color.Gray) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // --- 2. SEPET KARTI (Ekrana sığmadığında bu da kayacak) ---
        item(span = { GridItemSpan(gridCellsCount) }) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "Sepetteki Ürünler (${sepetList.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.DarkGray
                    )
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    if (sepetList.isEmpty()) {
                        Text(
                            "Sepetiniz boş",
                            modifier = Modifier.padding(8.dp),
                            color = Color.Gray,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            sepetList.forEach { (eleman, adet) ->
                                SepetSatiri(
                                    urun = eleman,
                                    adet = adet,
                                    onRemove = { viewModel.removeFromCart(eleman to adet) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Toplam Alışveriş tutarı = ${sepetList.sumOf { it.first.price * it.second }} ₺",
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = {
                            val isSaved = viewModel.saveCurrentList()
                            if (isSaved) {
                                onNavigateHomeScreen()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Listeyi Kaydet")
                    }
                }
            }
        }

        // --- 3. ARAMA ÇUBUĞU ---
        item(span = { GridItemSpan(gridCellsCount) }) {
            OutlinedTextField(
                value = aramaMetni,
                onValueChange = { viewModel.onSearchProductChange(it) },
                placeholder = { Text("Ürün Ara...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        // --- 4. KATEGORİ FİLTRESİ ---
        item(span = { GridItemSpan(gridCellsCount) }) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { viewModel.onCategoryChange(category) },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // --- 5. MARKET ÜRÜNLERİ ---
        items(
            items = filtrelenmisUrunler,
            key = { it.name }
        ) { eleman ->
            Box(modifier = Modifier.animateItemPlacement()) {
                val sepettekiAdet = sepetList.find { it.first == eleman }?.second ?: 0
                MarketItemCart(
                    item = eleman,
                    onIncrease = { viewModel.addToCart(eleman) },
                    onDecrease = { viewModel.decreaseFromCart(eleman) },
                    adet = sepettekiAdet
                )
            }
        }

        item(span = { GridItemSpan(gridCellsCount) }) {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SepetSatiri(urun: MarketItem, adet: Int, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.size(40.dp)) {
            Image(
                painter = painterResource(id = urun.photoID),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = urun.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = "$adet ${urun.unitType}", fontSize = 12.sp, color = Color.Gray)
            Text(text = "${urun.price} ₺", fontSize = 12.sp, color = Color.Gray)
            Text(text = "Toplam = ${urun.price * adet} ₺", fontSize = 12.sp, color = Color.Gray)
        }
        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Delete, contentDescription = "Sil", tint = Color.Red)
        }
    }
}