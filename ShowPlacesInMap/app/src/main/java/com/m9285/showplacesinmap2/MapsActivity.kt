package com.m9285.showplacesinmap2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-24.0, 200.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        //zoomipainikkeet käyttöön
        mMap.uiSettings.isZoomControlsEnabled = true

        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        // URL to JSON data, käytin JSON datan lataamiseen myjson.comia
        val url = "https://api.myjson.com/bins/1gz36h"
        // A request for retrieving a JSONObject response body at a given URL
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val places = response.getJSONArray("places")
                for (i in 0..(places.length()-1)) {
                    val hs = places.getJSONObject(i)

                    //otetaan paikan tiedot muuttujiin
                    val name = hs.getString("name")
                    val latitude = hs.getDouble("latitude")
                    val longitude = hs.getDouble("longitude")
                    //Toast.makeText(applicationContext,"$name $latitude $longitude",Toast.LENGTH_LONG).show()

                    //määritetään sijainti muuttujaksi paikka
                    val paikka = LatLng(latitude,longitude)
                    //lisätään Marker
                    mMap.addMarker(MarkerOptions().position(paikka).title("$name"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paikka, 14.0F))

                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.toString(),Toast.LENGTH_LONG).show()
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

        }
    }



