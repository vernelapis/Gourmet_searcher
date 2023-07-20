package com.example.myapplication.ui.home

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.models.Shop
import com.example.myapplication.repository.Api
import com.example.myapplication.repository.GPS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    var searchResultText = MutableLiveData<String>().apply {
        value = "This is search result Fragment"
    }

    private var _searchedGourmetList = MutableLiveData<List<Shop>>(emptyList())
    val searchedGourmetList: LiveData<List<Shop>> = _searchedGourmetList.distinctUntilChanged()

    fun searchGourmet(context: Context, navController: NavController){

        CoroutineScope(Dispatchers.IO).launch {
            try {
//                val location = GPS(context).getCurrentLocation()
//                val currentLat = location.latitude
//                val currentLng = location.longitude
//                val searchResultData = Api().apiTest(currentLat.toString(), currentLng.toString())
                val searchResultData = Api().apiTest("34.67", "135.52")
                println(searchResultData)
                if (searchResultData != null) {
                    _searchedGourmetList.postValue(searchResultData.shop_list)
                }

            } catch (e: Exception) {
                println(e)
                val activity = context as? Activity
                activity?.runOnUiThread {
                    navController.popBackStack()
                }
            }
        }
    }
}