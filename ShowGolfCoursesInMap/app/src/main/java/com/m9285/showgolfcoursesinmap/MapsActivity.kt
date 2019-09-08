package com.m9285.showgolfcoursesinmap

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    //kun markeria klikataan niin tämä metodi alkaa, clicklistener. classin perässä tulisi olla GoogleMap.OnMarkerClickListener
    //override fun onMarkerClick(marker: Marker?): Boolean {
    //    Toast.makeText(this,marker!!.title,Toast.LENGTH_LONG).show()
    //    return true
    //}

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
        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        //zoomauspainikkeet näkyviin
        mMap.uiSettings.isZoomControlsEnabled = true
        //zoomataan kartta Suomeen
        val suomi = LatLng(64.550260, 27.212719)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(suomi, 4.8F))

        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        // URL to JSON data
        val url = "http://ptm.fi/materials/golfcourses/golf_courses.json"
        // A request for retrieving a JSONObject response body at a given URL
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val courses = response.getJSONArray("courses")
                for (i in 0..(courses.length()-1)) {
                    val kurssit = courses.getJSONObject(i)

                    //otetaan tiedot muuttujiin
                    val type= kurssit.getString("type")
                    val lat = kurssit.getDouble("lat")
                    val lng = kurssit.getDouble("lng")
                    val course = kurssit.getString("course")
                    val address = kurssit.getString("address")
                    val phone = kurssit.getString("phone")
                    val email = kurssit.getString("email")
                    val web = kurssit.getString("web")
                    val imageurl = kurssit.getString("image")
                    val text = kurssit.getString("text")

                    Toast.makeText(applicationContext,"$type $address $phone $email $web $imageurl $text",Toast.LENGTH_LONG).show()
                    Toast.makeText(applicationContext, "Kurssityypit: Kulta = keltainen, Etu = punainen, Kulta/Etu = sininen, Tuntematon = vaaleansininen",Toast.LENGTH_LONG).show()
                    //määritetään markerin väri
                    //kurssityypit 4 erilaista "type" = Kulta, Kulta/Etu, Etu ja ?
                    var markerColor:Float = HUE_ORANGE

                    if (type == "Kulta") {
                        markerColor = HUE_YELLOW
                    }
                    if (type == "Etu") {
                        markerColor = HUE_ROSE
                    }
                    if (type == "Kulta/Etu") {
                        markerColor = HUE_AZURE
                    }
                    if (type == "?") {
                        markerColor = HUE_CYAN
                    }


                    //lisätään Marker kustakin paikasta kartalle
                    val paikka = LatLng(lat,lng)
                    //snippet on infoikkunan sisältö
                    mMap.addMarker(MarkerOptions()
                        .position(paikka).title("$course")
                        .icon(BitmapDescriptorFactory.defaultMarker(markerColor))
                        .snippet("$address , $phone , $email , $web"))

                    //lisätään click listener, klikkaus vie metodiin joka ylempänä määritelty
                    //mMap.setOnMarkerClickListener(this)

                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_LONG).show()
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
}
