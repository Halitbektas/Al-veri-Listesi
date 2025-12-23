package com.mobiluygulamagelistirme.myapplication.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobiluygulamagelistirme.myapplication.R
import com.mobiluygulamagelistirme.myapplication.common.model.MarketItem

@Composable
fun MarketItemCart(
    item: MarketItem,
    onIncrease: () -> Unit = {},
    onDecrease: () -> Unit = {},
    adet:Int
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // Kartlar birbirine yapışmasın diye dış boşluk
        shape = RoundedCornerShape(16.dp), // Daha yumuşak köşeler
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White) // Arka plan beyaz olsun
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp), // İçerik kenarlara yapışmasın
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. ÜRÜN RESMİ (Sabit alan ve şık görünüm)
            Box(
                modifier = Modifier
                    .height(120.dp) // Resim alanı sabit olsun
                    .fillMaxWidth()
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = item.photoID),
                    contentDescription = item.name,
                    contentScale = ContentScale.Fit, // Resmi sündürmeden sığdır
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 2. ÜRÜN BİLGİLERİ
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                // Ürün Adı
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1, // Çok uzun isim olursa alt satıra geçmesin
                    overflow = TextOverflow.Ellipsis
                )

                // Fiyat ve Birim
                Text(
                    text = "${item.price} ₺ / ${item.unitType}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary, // Tema rengiyle vurgula
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. SAYAÇ (ARTIR/AZALT) KISMI
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Butonları yay
            ) {
                // Azaltma Butonu (FilledTonal daha modern durur)
                FilledTonalIconButton(
                    onClick = onDecrease,
                    modifier = Modifier.size(36.dp) // Buton boyutu
                ) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = "Azalt")
                }

                // Adet Yazısı
                Text(
                    text = "${adet}", // Burası state'den gelmeli aslında
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                // Artırma Butonu
                FilledTonalButton( // Artı butonu daha belirgin olsun diye dolu buton
                    onClick = onIncrease,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(36.dp),
                    shape = RoundedCornerShape(8.dp) // Karemsi buton
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Artır")
                }
            }
        }
    }
}

// Preview İçin Örnek Veri
