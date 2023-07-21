package com.example.myapplication.ui.shop_information

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.Shop
import kotlin.properties.Delegates

class ShopInformationViewModel : ViewModel() {
    lateinit var gourmetData: Shop
    lateinit var currentLat: String
    lateinit var currentLng: String
}