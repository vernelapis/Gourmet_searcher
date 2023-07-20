package com.example.myapplication.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
//import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResultData(
    val api_version: String,
    val results_available: Int,
    val results_returned: String,
    val results_start: Int,
    @SerializedName("shop")
    val shop_list: List<Shop>
):Parcelable