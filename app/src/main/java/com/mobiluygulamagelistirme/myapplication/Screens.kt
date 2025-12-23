package com.mobiluygulamagelistirme.myapplication

sealed class Screens(val route:String) {
    data object HomeScreen: Screens(route = "homeScreen")
    data object ListScreen: Screens(route = "listScreen?id={id}")
}