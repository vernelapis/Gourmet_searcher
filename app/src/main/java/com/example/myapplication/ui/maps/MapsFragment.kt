package com.example.myapplication.ui.maps

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.models.Shop
import com.example.myapplication.ui.shop_information.ShopInformationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.abs
import kotlin.properties.Delegates


class MapsFragment : Fragment() {
    lateinit var shopLatLng: LatLng
    lateinit var currentLatLng: LatLng
    lateinit var gourmetData: Shop
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
//        val sydney = LatLng(-34.0, 151.0)
//        val centerLatLng = LatLng((shopLatLng.latitude+currentLatLng.latitude)/2, (shopLatLng.longitude+currentLatLng.longitude)/2)
        googleMap.addMarker(MarkerOptions().position(shopLatLng).title(gourmetData.name))
        googleMap.addMarker(MarkerOptions().position(currentLatLng).title("現在地")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
        zoomMap(shopLatLng, currentLatLng, googleMap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val shopInformationViewModel = ViewModelProvider(requireParentFragment()).get(
            ShopInformationViewModel::class.java)
        gourmetData = shopInformationViewModel.gourmetData
        shopLatLng = LatLng(gourmetData.lat, gourmetData.lng)
        currentLatLng = LatLng(shopInformationViewModel.currentLat.toDouble(), shopInformationViewModel.currentLng.toDouble())

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun zoomMap(shopLatLng: LatLng, currentLatLng: LatLng, googleMap: GoogleMap) {
        val latRange = abs(shopLatLng.latitude - currentLatLng.latitude) * 0.01
        val lngRange = abs(shopLatLng.longitude - currentLatLng.longitude) * 0.02
        val latitude  = (shopLatLng.latitude+currentLatLng.latitude)/2
        val longitude = (shopLatLng.longitude+currentLatLng.longitude)/2

        // 表示する東西南北の緯度経度を設定
        val south = latitude * (1 - latRange)
        val west = longitude * (1 - lngRange)
        val north = latitude * (1 + latRange)
        val east = longitude * (1 + lngRange)

        // LatLngBounds (LatLng southwest, LatLng northeast)
        val bounds = LatLngBounds.builder()
            .include(LatLng(south, west))
            .include(LatLng(north, east))
            .build()
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels

        // static CameraUpdate.newLatLngBounds(LatLngBounds bounds, int width, int height, int padding)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, 0))
    }
}