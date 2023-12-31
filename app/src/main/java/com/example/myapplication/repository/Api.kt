package com.example.myapplication.repository

import com.example.myapplication.models.Genre
import com.example.myapplication.models.SearchResultData
import com.example.myapplication.models.Shop
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

@Suppress("ThrowableNotThrown")
class Api {

    private val BASE_URL = "http://webservice.recruit.co.jp/hotpepper/gourmet/v1/?"
    val API_KEY = "6b4f5d742fca2ad3"

    val SEARCH_COUNT = "20"

    fun gourmetSearch(
        lat:String,
        lng:String,
        range:Int,
        start:Int,
        genre:String = "",
        budget:String = "",
        party_capacity:Int = 1,
    ): SearchResultData?{

        val baseParameters = listOf<Pair<String,String>>(
            "key" to API_KEY,
            "format" to "json",
            "lat" to lat,
            "lng" to lng,
            "range" to range.toString(),
            "start" to start.toString(),
            "genre" to genre,
            "budget" to budget,
            "party_capacity" to party_capacity.toString(),
            "count" to SEARCH_COUNT
        )
        println(baseParameters)

        /// GETリクエスト送信！
        val (_, _, result) = Fuel.get(BASE_URL, baseParameters).responseJson()

        return when(result) {
            is Result.Failure -> {
                /// リクエスト失敗・エラー
                val ex = result.getException()
                //                    Log.d(TAG, "Failure : "+ex.toString())
                println(ex)
                null
            }
            is Result.Success -> {
                /// レスポンス正常取得
                /// JSONObjectに変換
                val results = result.get().obj().getJSONObject("results")
                val rowShops = results.getJSONArray("shop")
                val checkedData = replaceEmptyValues(rowShops)
                println(checkedData)
                val shopList = mutableListOf<Shop>()
                for (i in 0 until checkedData.length()) {
                    shopList.add(Gson().fromJson(checkedData.getString(i), Shop::class.java))
                }
                val searchResultData = SearchResultData(
                    api_version = results.getString("api_version"),
                    results_available = results.getInt("results_available"),
                    results_returned = results.getString("results_returned"),
                    results_start = results.getInt("results_start"),
                    shop_list = shopList
                )
                searchResultData
            }
        }
    }

//    変換に邪魔な""を書き変え　intは一旦0に
    private fun replaceEmptyValues(jsonArray: JSONArray): JSONArray {
        val modifiedArray = JSONArray()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            for (key in jsonObject.keys()) {
                val value = jsonObject[key]
                if (value is String && value.isEmpty()) {
                    if (key == "party_capacity" || key == "capacity"){
                        jsonObject.put(key, "0")
                    }else{
                        jsonObject.put(key, "データなし")
                    }
                } else if (value is JSONObject) {
                    val nestedObject = replaceEmptyValues(JSONArray().put(value))
                    jsonObject.put(key, JSONObject(nestedObject.getJSONObject(0).toString()))
                }
            }
            modifiedArray.put(jsonObject)

        }
        return modifiedArray
    }
}