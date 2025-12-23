package com.mobiluygulamagelistirme.myapplication.feature.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mobiluygulamagelistirme.myapplication.MyApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(myapplication().container.repository)
        }
        initializer {
            CreateListViewModel(myapplication().container.repository)
        }
    }
}

fun CreationExtras.myapplication(): MyApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)