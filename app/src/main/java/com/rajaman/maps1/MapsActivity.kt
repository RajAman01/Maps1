package com.rajaman.maps1

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val btn = findViewById<Button>(R.id.locationBtn)
//        var location = 0
//        btn.setOnClickListener {
//            Toast.makeText(this,"Waiting To Get Location",Toast.LENGTH_LONG).show()
//            location = 50
//        }

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var loc = 60
        val f1 = LocationServices.getFusedLocationProviderClient(this)
        locationBtn.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),500
                )
            }else{
                f1.lastLocation.addOnSuccessListener { location: Location? ->
                    if(location==null){
                        Toast.makeText(this,"Location Not Found",Toast.LENGTH_LONG).show()
                        val myLoc = LatLng(50.0, 151.0)
                        mMap.addMarker(MarkerOptions().position(myLoc).title("Not Found"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc))
                    }
                    else{
                        val latitude:Double = location.latitude
                        val longitude:Double = location.longitude
                        Toast.makeText(this,"Waiting To Get Location",Toast.LENGTH_LONG).show()
                        loc = 151
                        val sydney = LatLng( latitude,longitude)
                        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                    }
                }
            }



        }

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, loc.toDouble())
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }
}