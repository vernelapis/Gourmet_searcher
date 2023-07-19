package com.example.myapplication.models
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestGourmet(
    val id: String,
    val name: String,
    val access: String,
    val logo_image: String,// url
) : Parcelable

data class GourmetList(
    val rootkey: List<GourmetData>
)

data class GourmetData(
    val id: Int,
    val name: String,
    val logo_image: String,// url
    val name_kana: String,
    val address: String,
    val station_name: String,
    val ktai_coupon: Int,
    val large_service_area: List<Any>,
    val service_area: List<Any>,
    val large_area: List<Any>,
    val middle_area: List<Any>,
    val small_area: List<Any>,
    val lat: Float,
    val lng: Float,
    val genre: List<Any>,
    val sub_genre: List<Any>,
    val budget: List<Any>,
    val budget_memo: String,
    val catch: String,
    val capacity: Int,
    val access: String,
    val mobile_access: String,
    val urls: List<Any>,
    val photo: List<Any>,
    val open: String,
    val close: String,
    val party_capacity: Int,
    val wifi: String,
    val wedding: String,
    val course: String,
    val free_drink: String,
    val free_food: String,
    val private_room: String,
    val horigotatsu: String,
    val tatami: String,
    val card: String,
    val non_smoking: String,
    val charter: String,
    val ktai: String,
    val parking: String,
    val barrier_free: String,
    val other_memo: String,
    val sommelier: String,
    val open_air: String,
    val show: String,
    val equipment: String,
    val karaoke: String,
    val band: String,
    val tv: String,
    val english: String,
    val pet: String,
    val child: String,
    val lunch: String,
    val midnight: String,
    val shop_detail_memo: String,
    val coupon_urls: List<Any>?,
)

