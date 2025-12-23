package com.mobiluygulamagelistirme.myapplication.common.model

import java.sql.Date


data class CartItem(val itemName: String,
               val Price: Double,
               val UnitType:String,
               val Value:Int,
               var isChecked: Boolean,
) {}