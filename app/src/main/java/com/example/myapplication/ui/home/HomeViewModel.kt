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
import com.example.myapplication.repository.GPS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

//    TODO: コメントアウト解除
    private lateinit var currentLat: String
    private lateinit var currentLng: String
//    val currentLat = "34.67"
//    val currentLng = "135.52"

    var range = 2
    var genre = 0
    private val genreCode = listOf("","G001","G002","G003","G004","G005","G006","G007","G008",
        "G017","G009","G010","G011","G012","G013","G016","G014","G015")
    var budget = 0
    private val budgetCode = listOf("","B009","B010","B011","B001","B002","B003","B008","B004","B005","B006","B012","B013","B014")
    private var searchStart = 1
    private var resultCount = 30

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private var _searchedGourmetList = MutableLiveData<List<Shop>>(emptyList())
    val searchedGourmetList: LiveData<List<Shop>> = _searchedGourmetList.distinctUntilChanged()

    fun searchGourmet(context: Context){
        CoroutineScope(Dispatchers.IO).launch {
                searchStart = 1
                val location = GPS(context).getCurrentLocation()
                currentLat = location.latitude.toString()
                currentLng = location.longitude.toString()
                val searchResultData = Api().gourmetSearch(currentLat, currentLng,
                    range+1,searchStart, genreCode[genre], budgetCode[budget])
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
                    val searchResultData = Api().gourmetSearch(currentLat, currentLng,
                        range+1, searchStart, genreCode[genre], budgetCode[budget])
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