
package ie.setu.social_media_app_mad1.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import ie.setu.social_media_app_mad1.databinding.ActivitySocialMediaMapBinding
import ie.setu.social_media_app_mad1.databinding.ContentSocialMediaMapBinding
import ie.setu.social_media_app_mad1.main.MainApp


class SocialMediaMapActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {


    private lateinit var binding: ActivitySocialMediaMapBinding
    private lateinit var contentBinding: ContentSocialMediaMapBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySocialMediaMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentSocialMediaMapBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        app = application as MainApp
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }

    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.users.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.username).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)
            map.addMarker(options)?.tag = it.id
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val tag = marker.tag as Long
        val user = app.users.findById(tag)
        contentBinding.currentTitle.text = marker.title
        if (user != null) {
            contentBinding.currentDescription.text = user.caption
        }
        if (user != null) {
            Picasso.get().load(user.profilepic).into(contentBinding.imageView2)
        }

        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }
}