package com.mobiluygulamagelistirme.myapplication

import android.app.Application
import com.mobiluygulamagelistirme.myapplication.data.ShoppingLocalDataSource
import com.mobiluygulamagelistirme.myapplication.data.ShoppingRepository

class MyApplication : Application() {
    // Tüm uygulama genelinde erişilecek Repository
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}

// Basit bir Dependency Container
class AppContainer(context: android.content.Context) {
    private val dataSource = ShoppingLocalDataSource(context)
    val repository = ShoppingRepository(dataSource)
}