package com.example.myapplication.ui.home

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.NavController
import com.example.myapplication.models.Shop
import com.example.myapplication.repository.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private lateinit var currentLat: String
    private lateinit var currentLng: String
    private var range = 3
    private var searchStart = 1
    private var resultCount = 30

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private var _searchedGourmetList = MutableLiveData<List<Shop>>(emptyList())
    val searchedGourmetList: LiveData<List<Shop>> = _searchedGourmetList.distinctUntilChanged()

    fun searchGourmet(){
        CoroutineScope(Dispatchers.IO).launch {
                searchStart = 1
//                val location = GPS(context).getCurrentLocation()
//                currentLat = location.latitude.toString()
//                currentLng = location.longitude.toString()
//                val searchResultData = Api().apiTest(currentLat, currentLng)
                val searchResultData = Api().gourmetSearch("34.67", "135.52", range, searchStart)
                println(searchResultData)
                if (searchResultData != null) {
                    _searchedGourmetList.postValue(searchResultData.shop_list)
                    searchStart += searchResultData.results_returned.toInt()
                    resultCount = searchResultData.results_available
                }
        }
    }

    fun searchMoreGourmet(){
        if (searchStart <= resultCount && searchStart != 1){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    //                val searchResultData = Api().apiTest(currentLat, currentLng)
                    val searchResultData = Api().gourmetSearch("34.67", "135.52", range, searchStart)
                    println(searchResultData)
                    if (searchResultData != null) {
                        val currentList = _searchedGourmetList.value!!.toMutableList()
                        currentList.addAll(searchResultData.shop_list)
                        _searchedGourmetList.postValue(currentList)
                        searchStart += searchResultData.results_returned.toInt()
                    }

                } catch (e: Exception) {
                    println(e)
                }
            }
        }

    }
}