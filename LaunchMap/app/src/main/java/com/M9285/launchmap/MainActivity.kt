package com.M9285.launchmap

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    lateinit var mAdView : AdView
    lateinit var mAdView2: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mainokset
        MobileAds.initialize(this, R.string.sovelluksen_id.toString());
        mAdView = findViewById(R.id.adView)
        mAdView2 = findViewById(R.id.adView2)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView2.loadAd(adRequest)

    }

    fun showMap(view: View) {
    //luodaan muuttujat lat ja lng, tiedot haetaan käyttäjän syöttämistä numeroista ja muunnetaan sopiviksi
    val lat = latitudeText.text.toString().toDouble()
    val lng = longitudeText.text.toString().toDouble()

    //luodaan muuttuja location joka on merkkijonot (URI) lat ja lng parsittuna
    val location = Uri.parse("geo:$lat,$lng")
    //luodaan muuttuja, jolla haetaan lokaation tiedot
    val mapIntent = Intent(Intent.ACTION_VIEW, location)

    val activities: List<ResolveInfo> = packageManager.queryIntentActivities(mapIntent, 0)
    //jos activities saa arvon, niin isIntentSafe saa arvoksi true -> aktiviteetti aloitetaan (rivi 34)
    val isIntentSafe: Boolean = activities.isNotEmpty()

    if (isIntentSafe) {
        startActivity(mapIntent)
    } else {
        //avaa viesti-ikkunan käyttäjälle jos aktiviteetti ei onnistu
        Toast.makeText(this, "There is no activity to handle map intent!", Toast.LENGTH_LONG).show();
     }

    }



}
