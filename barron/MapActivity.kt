package com.barron

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.barron.databinding.ActivityMapBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Places
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "TU_API_KEY")
        }
        placesClient = Places.createClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment

        mapFragment.getMapAsync(this)

        binding.btnLocation.setOnClickListener {
            getUserLocation()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        getUserLocation()
    }

    private fun getUserLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }

        googleMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val userLatLng = LatLng(it.latitude, it.longitude)

                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(userLatLng, 15f)
                )

                searchNearbyStores(userLatLng)
            }
        }
    }

    private fun searchNearbyStores(location: LatLng) {

        val placeFields = listOf(
            com.google.android.libraries.places.api.model.Place.Field.NAME,
            com.google.android.libraries.places.api.model.Place.Field.LAT_LNG
        )

        val request = FindCurrentPlaceRequest.newInstance(placeFields)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        placesClient.findCurrentPlace(request)
            .addOnSuccessListener { response ->

                googleMap.clear()

                for (placeLikelihood in response.placeLikelihoods) {
                    val place = placeLikelihood.place

                    // FILTRAR SOLO TIENDAS
                    if (place.name?.contains("tienda", true) == true ||
                        place.name?.contains("store", true) == true ||
                        place.name?.contains("shop", true) == true
                    ) {
                        place.latLng?.let { latLng ->
                            googleMap.addMarker(
                                MarkerOptions()
                                    .position(latLng)
                                    .title(place.name)
                            )
                        }
                    }
                }
            }
    }
}