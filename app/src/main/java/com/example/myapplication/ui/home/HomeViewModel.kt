package com.example.myapplication.ui.home

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.myapplication.models.TestGourmet
import com.example.myapplication.repository.Api
import com.example.myapplication.repository.GPS
import com.example.myapplication.ui.search_result.SearchResultFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private  var _currentLat = 34.67
    private var _currentLng = 135.52

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    var searchResultText = MutableLiveData<String>().apply {
        value = "This is search result Fragment"
    }
    var searchCallCount = MutableLiveData<Int>().apply { value = 0 }

    var searchedGourmetList = MutableLiveData<MutableList<TestGourmet>>()
        .apply { value = mutableListOf<TestGourmet>(TestGourmet("test_name", "test_access")) }

    fun searchGourmet(context: Context, navController: NavController){

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val location = GPS(context).getCurrentLocation()
                _currentLat = location.latitude
                _currentLng = location.longitude
                var nowCount = searchCallCount.value ?: 0
                searchCallCount.postValue(++nowCount)
//                var _text = Api().apiTest(_currentLat.toString(), _currentLng.toString())
//                _text = "Count : ${searchCallCount.value}\n$_text"
//                searchResultText.postValue(_text)
                val gourmetList = Api().apiTest(_currentLat.toString(), _currentLng.toString())
                println(gourmetList)
                searchedGourmetList.postValue(gourmetList)

            } catch (e: Exception) {
                val activity = context as? Activity
                activity?.runOnUiThread {
                    navController.popBackStack()
                }
            }
        }


    }
}