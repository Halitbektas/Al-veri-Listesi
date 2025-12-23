package com.mobiluygulamagelistirme.myapplication.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mobiluygulamagelistirme.myapplication.common.model.CartItem

// --- 1. AKTİF LİSTELER İÇİN BÜYÜK KART (Eski Tasarım + Silme Butonu) ---
@Composable
fun ActiveListCart(
    listName: String,
    date: String,
    itemList: List<CartItem>,
    onItemCheckedChange: (CartItem, Boolean) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit // Aktif listeyi de silmek istersin diye ekledim
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEditClick() }, // Karta tıklayınca düzenlemeye git
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // --- BAŞLIK VE TARİH VE SİL BUTONU ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = listName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                // Aktif liste silme butonu (Başlığın sağına koyduk)
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Listeyi Sil", tint = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(8.dp))

            // --- TABLO BAŞLIKLARI ---
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("Ürün", style = MaterialTheme.typography.labelMedium, color = Color.Gray, modifier = Modifier.weight(0.4f))
                Text("Miktar", style = MaterialTheme.typography.labelMedium, color = Color.Gray, modifier = Modifier.weight(0.3f))
                Text("Fiyat", style = MaterialTheme.typography.labelMedium, color = Color.Gray, modifier = Modifier.weight(0.2f))
                Spacer(modifier = Modifier.width(40.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- ÜRÜNLER LİSTESİ ---
            Column(modifier = Modifier.fillMaxWidth()) {
                itemList.forEach { item ->
                    CartItemRow(
                        item = item,
                        onCheckedChange = { isChecked ->
                            onItemCheckedChange(item, isChecked)
                        }
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.2f), thickness = 0.5.dp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- TOPLAM TUTAR ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val total = itemList.sumOf { it.Price }
                Text(text = "Toplam: ", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "%.2f TL".format(total),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

// --- 2. GEÇMİŞ LİSTELER İÇİN KÜÇÜK KART (Yeni Tasarım) ---
@Composable
fun CompactHistoryCart(
    listName: String,
    date: String,
    totalPrice: Double,
    itemCount: Int,
    onDeleteClick: () -> Unit,
    onRecreateClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)) // Hafif gri
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // SOL: Bilgiler
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = listName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.LineThrough, // Geçmiş olduğu belli olsun
                    color = Color.Gray
                )
                Text(
                    text = "$date • $itemCount Ürün",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "%.2f TL".format(totalPrice),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // SAĞ: Butonlar (Tekrarla ve Sil)
            Row {
                IconButton(onClick = onRecreateClick) {
                    Icon(Icons.Default.Refresh, contentDescription = "Tekrarla", tint = Color(0xFF4CAF50))
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Sil", tint = Color.Red)
                }
            }
        }
    }
}

// --- YARDIMCI SATIR BİLEŞENİ (Eski koddan) ---
@Composable
fun CartItemRow(
    item: CartItem,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!item.isChecked) }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textDecoration = if (item.isChecked) TextDecoration.LineThrough else null
        val textColor = if (item.isChecked) Color.Gray else Color.Black

        Text(
            text = item.itemName,
            style = MaterialTheme.typography.bodyMedium.copy(textDecoration = textDecoration),
            color = textColor,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = "${item.Value} ${item.UnitType}",
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = "${item.Price}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
            modifier = Modifier.weight(0.2f)
        )
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
}