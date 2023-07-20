package com.example.myapplication.repository

import android.content.ContentValues
import android.util.Log
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

    fun apiTest(lat:String, lng:String): SearchResultData?{

        val baseParameters = listOf<Pair<String,String>>(
            "key" to API_KEY,
            "format" to "json",
            "lat" to lat,
            "lng" to lng,
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
//                val searchResultData = Gson().fromJson(checkedData, SearchResultData::class.java)
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

//    private fun nullFixer(rowShops:JSONArray):String{
//        fun replaceEmptyStrings(jsonArray: JSONArray): JSONArray {
//            val modifiedArray = JSONArray()
//            for (i in 0 until jsonArray.length()) {
//                val element = jsonArray.optString(i, "") // Get the element as a String, empty string if null
//                if (element.isEmpty()) {
//                    if ()
//                    modifiedArray.put("情報なし") // Replace empty strings with "情報なし"
//                } else {
//                    modifiedArray.put(element)
//                }
//            }
//            return modifiedArray
//        }
    fun replaceEmptyValues(jsonArray: JSONArray): JSONArray {
        val modifiedArray = JSONArray()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            println(jsonObject)
            for (key in jsonObject.keys()) {
                val value = jsonObject[key]
                println(value)
                if (value is String && value.isEmpty()) {
                    if (key == "party_capacity" || key == "capacity"){
                        jsonObject.put(key, "0")
                    }else{
                        jsonObject.put(key, "データなし")
                    }
                } else if (value is JSONObject) {
                    val nestedObject = replaceEmptyValues(JSONArray().put(value)) // Pass the JSONObject as a JSONArray
                    jsonObject.put(key, JSONObject(nestedObject.getJSONObject(0).toString())) // Convert the JSONArray back to JSONObject
                }
                println(value)
            }
            println(jsonObject)
            modifiedArray.put(jsonObject)

        }
        return modifiedArray
    }
}