package com.mobiluygulamagelistirme.myapplication.feature

import com.mobiluygulamagelistirme.myapplication.R
import com.mobiluygulamagelistirme.myapplication.common.model.MarketItem

val samplemarketList = listOf(
    // MEYVE & SEBZE
    MarketItem("Domates", R.drawable.elma, 25.0, "kg", "Meyve & Sebze"),
    MarketItem("Salatalık", R.drawable.salata, 20.0, "kg", "Meyve & Sebze"),
    MarketItem("Biber", R.drawable.biber, 30.0, "kg", "Meyve & Sebze"),
    MarketItem("Patlıcan", R.drawable.patlican, 28.0, "kg", "Meyve & Sebze"),
    MarketItem("Patates", R.drawable.patates, 18.0, "kg", "Meyve & Sebze"),
    MarketItem("Soğan", R.drawable.sogan, 12.0, "kg", "Meyve & Sebze"),
    MarketItem("Kıvırcık", R.drawable.kivircik, 15.0, "adet", "Meyve & Sebze"),
    MarketItem("Limon", R.drawable.limon, 25.0, "file", "Meyve & Sebze"),
    MarketItem("Muz", R.drawable.muz, 45.0, "kg", "Meyve & Sebze"),
    MarketItem("Elma", R.drawable.elma, 25.0, "kg", "Meyve & Sebze"),
    MarketItem("Portakal", R.drawable.portakal, 20.0, "kg", "Meyve & Sebze"),

    // SÜT & KAHVALTILIK
    MarketItem("Tam Yağlı Süt", R.drawable.suttam, 35.0, "lt", "Süt & Kahvaltılık"),
    MarketItem("Yarım Yağlı Süt", R.drawable.sutyarim, 32.0, "lt", "Süt & Kahvaltılık"),
    MarketItem("Beyaz Peynir", R.drawable.beyazp, 180.0, "kg", "Süt & Kahvaltılık"),
    MarketItem("Kaşar Peyniri", R.drawable.kasarpeynir, 220.0, "kg", "Süt & Kahvaltılık"),
    MarketItem("Tereyağı", R.drawable.tereyagi, 250.0, "adet", "Süt & Kahvaltılık"),
    MarketItem("Yumurta (15'li)", R.drawable.yumurta, 65.0, "koli", "Süt & Kahvaltılık"),
    MarketItem("Siyah Zeytin", R.drawable.siyahzeytin, 140.0, "kg", "Süt & Kahvaltılık"),
    MarketItem("Yeşil Zeytin", R.drawable.yesilzeytin, 120.0, "kg", "Süt & Kahvaltılık"),
    MarketItem("Bal", R.drawable.bal, 150.0, "kavanoz", "Süt & Kahvaltılık"),
    MarketItem("Çilek Reçeli", R.drawable.cilekrecel, 75.0, "kavanoz", "Süt & Kahvaltılık"),

    // ET & TAVUK
    MarketItem("Dana Kıyma", R.drawable.danakiyma, 450.0, "kg", "Et & Tavuk"),
    MarketItem("Dana Kuşbaşı", R.drawable.danakus, 480.0, "kg", "Et & Tavuk"),
    MarketItem("Tavuk Göğsü", R.drawable.tavukgogus, 180.0, "kg", "Et & Tavuk"),
    MarketItem("Tavuk Baget", R.drawable.baget, 140.0, "kg", "Et & Tavuk"),
    MarketItem("Sucuk", R.drawable.sucuk, 350.0, "kang", "Et & Tavuk"),
    MarketItem("Salam", R.drawable.salam, 90.0, "paket", "Et & Tavuk"),
    MarketItem("Sosis", R.drawable.sosis, 110.0, "paket", "Et & Tavuk"),

    // TEMEL GIDA
    MarketItem("Ayçiçek Yağı", R.drawable.cicekyagi, 45.0, "lt", "Temel Gıda"),
    MarketItem("Zeytinyağı", R.drawable.zeytinyagi, 280.0, "lt", "Temel Gıda"),
    MarketItem("Pirinç", R.drawable.pirinc, 60.0, "kg", "Temel Gıda"),
    MarketItem("Bulgur", R.drawable.bulgur, 35.0, "kg", "Temel Gıda"),
    MarketItem("Kırmızı Mercimek", R.drawable.mercimek, 40.0, "kg", "Temel Gıda"),
    MarketItem("Nohut", R.drawable.nohut, 55.0, "kg", "Temel Gıda"),
    MarketItem("Makarna", R.drawable.makarna, 18.0, "paket", "Temel Gıda"),
    MarketItem("Salça", R.drawable.salca, 45.0, "kavanoz", "Temel Gıda"),
    MarketItem("Un", R.drawable.un, 30.0, "kg", "Temel Gıda"),
    MarketItem("Toz Şeker", R.drawable.seker, 35.0, "kg", "Temel Gıda"),
    MarketItem("Çay (1kg)", R.drawable.cay, 140.0, "paket", "Temel Gıda"),

    // TEMİZLİK & KİŞİSEL BAKIM
    MarketItem("Bulaşık Deterjanı", R.drawable.bulasik, 50.0, "adet", "Temizlik"),
    MarketItem("Çamaşır Suyu", R.drawable.camasir, 45.0, "lt", "Temizlik"),
    MarketItem("Yüzey Temizleyici", R.drawable.yuzey, 35.0, "lt", "Temizlik"),
    MarketItem("Tuvalet Kağıdı (12'li)", R.drawable.tuvaletkagidi, 120.0, "paket", "Temizlik"),
    MarketItem("Kağıt Havlu (6'lı)", R.drawable.kagithavlu, 90.0, "paket", "Temizlik"),
    MarketItem("Şampuan", R.drawable.sampuan, 85.0, "adet", "Kişisel Bakım"),
    MarketItem("Diş Macunu", R.drawable.dismacunu, 65.0, "adet", "Kişisel Bakım"),
    MarketItem("Sıvı Sabun", R.drawable.sivisabun, 40.0, "adet", "Kişisel Bakım"),

    // ATIŞTIRMALIK
    MarketItem("Cips", R.drawable.cips, 35.0, "paket", "Atıştırmalık"),
    MarketItem("Çikolata", R.drawable.cikolata, 25.0, "adet", "Atıştırmalık"),
    MarketItem("Bisküvi", R.drawable.biskuvi, 15.0, "paket", "Atıştırmalık"),
    MarketItem("Kola (2.5L)", R.drawable.kola, 40.0, "adet", "Atıştırmalık")
)