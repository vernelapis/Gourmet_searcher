package com.example.myapplication.repository

import android.content.ContentValues
import android.util.Log
import com.example.myapplication.models.TestGourmet
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result

@Suppress("ThrowableNotThrown")
class Api {

    private val BASE_URL = "http://webservice.recruit.co.jp/hotpepper/gourmet/v1/?"
    val API_KEY = "6b4f5d742fca2ad3"

    fun apiTest(lat:String, lng:String): MutableList<TestGourmet>?{

        val baseParameters = listOf<Pair<String,String>>(
            "key" to API_KEY,
            "format" to "json",
            "lat" to lat,
            "lng" to lng,
        )

        /// GETリクエスト送信！
        val (_, _, result) = Fuel.get(BASE_URL, baseParameters).responseJson()

        return when(result) {
            is Result.Failure -> {
//                /// リクエスト失敗・エラー
//                val ex = result.getException()
//                //                    Log.d(TAG, "Failure : "+ex.toString())
//                println(ex)
//                ex.toString()
                null
            }
            is Result.Success -> {
                /// レスポンス正常取得
                /// JSONObjectに変換
                val data = result.get().obj()
//                Log.d(
//                    ContentValues.TAG, "Responsed JSON : "
//                            +data.toString())
//                data.toString()
                val dataList = mutableListOf<TestGourmet>()
                println(data)
                val results =  data.getJSONObject("results")

                val resultsArray = results.getJSONArray("shop")
                println(resultsArray)
                for (i in 0 until resultsArray.length()) {
                    val result = resultsArray.getJSONObject(i)
                    val name = result.getString("name")
                    val access = result.getString("access")

                    // DataItemオブジェクトを作成し、リストに追加
                    val dataItem = TestGourmet(name,access)
                    println(dataItem)
                    dataList.add(dataItem)
                }
                dataList
            }
        }
    }
}