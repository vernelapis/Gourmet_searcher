package com.example.myapplication.adapter

import android.widget.ImageView
import coil.load
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.example.myapplication.R




@BindingAdapter("logoImageUrl")
fun bindLogoImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.ic_home_black_24dp)
            error(R.drawable.ic_dashboard_black_24dp)
        }
    }
}