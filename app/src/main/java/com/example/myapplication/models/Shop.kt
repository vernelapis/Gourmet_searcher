package com.example.myapplication.models
import android.os.Parcelable
//import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shop(
    val access: String,
    val address: String,
    val band: String,
    val barrier_free: String,
    val budget: Budget,
    val budget_memo: String,
    val capacity: Int,
    val card: String,
    val `catch`: String,
    val charter: String,
    val child: String,
    val close: String,
    val coupon_urls: CouponUrls,
    val course: String,
    val english: String,
    val free_drink: String,
    val free_food: String,
    val genre: Genre,
    val horigotatsu: String,
    val id: String,
    val karaoke: String,
    val ktai_coupon: Int,
    val large_area: LargeArea,
    val large_service_area: LargeServiceArea,
    val lat: Double,
    val lng: Double,
    val logo_image: String,
    val lunch: String,
    val middle_area: MiddleArea,
    val midnight: String,
    val mobile_access: String,
    val name: String,
    val name_kana: String,
    val non_smoking: String,
    val `open`: String,
    val other_memo: String,
    val parking: String,
    val party_capacity: Int,
    val pet: String,
    val photo: Photo,
    val private_room: String,
    val service_area: ServiceArea,
    val shop_detail_memo: String,
    val show: String,
    val small_area: SmallArea,
    val station_name: String,
    val tatami: String,
    val tv: String,
    val urls: Urls,
    val wedding: String,
    val wifi: String
):Parcelable

@Parcelize
data class Budget(
    val average: String,
    val code: String,
    val name: String
):Parcelable

@Parcelize
data class CouponUrls(
    val pc: String,
    val sp: String
):Parcelable

@Parcelize
data class Genre(
    val `catch`: String,
    val code: String,
    val name: String
):Parcelable

@Parcelize
data class LargeArea(
    val code: String,
    val name: String
):Parcelable

@Parcelize
data class LargeServiceArea(
    val code: String,
    val name: String
):Parcelable

@Parcelize
data class MiddleArea(
    val code: String,
    val name: String
):Parcelable

@Parcelize
data class Photo(
    val mobile: Mobile,
    val pc: Pc
):Parcelable

@Parcelize
data class Pc(
    val l: String,
    val m: String,
    val s: String
):Parcelable

@Parcelize
data class Mobile(
    val l: String,
    val s: String
):Parcelable

@Parcelize
data class ServiceArea(
    val code: String,
    val name: String
):Parcelable

@Parcelize
data class SmallArea(
    val code: String,
    val name: String
):Parcelable

@Parcelize
data class Urls(
    val pc: String
):Parcelable