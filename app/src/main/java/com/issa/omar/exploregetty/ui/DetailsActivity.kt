package com.issa.omar.exploregetty.ui

import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.issa.omar.exploregetty.model.BusinessDetails
import kotlinx.android.synthetic.main.activity_details.*
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.GoogleMap
import com.issa.omar.exploregetty.adapters.HoursAdapter
import com.issa.omar.exploregetty.R
import com.issa.omar.exploregetty.adapters.PhotosAdapter
import com.issa.omar.exploregetty.databinding.ActivityDetailsBinding
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    private val viewModel by lazy {
        return@lazy ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

    private lateinit var mapView: MapView
    private lateinit var hoursList: ListView
    private lateinit var photosList: RecyclerView
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        setSupportActionBar(toolbar)
        binding.toolbarLayout.title = ""
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        mapView = binding.businessDetails.mapView
        hoursList = binding.businessDetails.hours
        photosList = binding.businessDetails.photos
        initMapView(savedInstanceState)
        viewModel.getDetails().observe(this, Observer<BusinessDetails> {
            updateView()
        })
    }

    private fun updateView() {
        viewModel.setDetails()
        addMapMarker()
        hoursList.adapter = HoursAdapter(this, viewModel.hours)
        setupRecyclerView()
        showDetails()
        Picasso.get().load(viewModel.imageUrl).into(binding.headerImage)
        binding.toolbarLayout.title = viewModel.name.value
    }

    private fun setupRecyclerView() {
        photosList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        photosList.adapter = PhotosAdapter(this, viewModel.photos)
    }

    private fun showDetails() {
        binding.businessDetails.scrollView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mapView.onCreate(mapViewBundle)

        mapView.getMapAsync(this)
    }

    private fun addMapMarker() {
        val latLng = viewModel.getLatLong()
        mapView.getMapAsync { map ->
            map.addMarker(MarkerOptions().position(latLng).title(viewModel.name.value))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f))
        }
    }

    override fun onMapReady(map: GoogleMap) {
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }

        mapView.onSaveInstanceState(mapViewBundle)
    }
}
