package com.m9285.golfapplication

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_course.*
import org.json.JSONObject

class CourseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        // get data from intent
        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val kurssiString = bundle!!.getString("kurssi")
            val kurssi = JSONObject(kurssiString)

            //val course = kurssi["course"]
            //Toast.makeText(this, "Name is $course", Toast.LENGTH_LONG).show()

            //asetetaan tuodut tiedot tekstikenttiin
            courseTextView2.text = kurssi["course"].toString()
            addressTextView2.text = kurssi["address"].toString()
            phoneTextView2.text = kurssi["phone"].toString()
            emailTextView2.text = kurssi["email"].toString()
            moreInfoHere.text = kurssi["text"].toString()
            kotisivutTextView.text = kurssi["web"].toString()
            sijaintiTextView.text = "Näytä kartalla "+ kurssi["address"].toString()

            //kuva
            val url2 = kurssi["image"].toString()
            val kokoUrl2 = "http://ptm.fi/materials/golfcourses/" + url2
            Glide.with(imageView2.context).load(kokoUrl2).into(imageView2)

        }

    }

    // avaa paikan kotisivut
    fun naytaKotivisivut(v: View) {

        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val kurssiString = bundle!!.getString("kurssi")
            val kurssi = JSONObject(kurssiString)

            val weburl = kurssi["web"].toString()

            // Build the intent
            val web = Uri.parse(weburl)
            val webIntent = Intent(Intent.ACTION_VIEW, web)

            // Always use string resources for UI text.
            val title = resources.getString(R.string.otsikko)
            // Create intent to show chooser
            val chooser = Intent.createChooser(webIntent, title)

            // Verify the intent will resolve to at least one activity
            if (webIntent.resolveActivity(packageManager) != null) {
                startActivity(chooser)
        }
        }
    }

    fun avaaKartta(view: View) {

        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val kurssiString = bundle!!.getString("kurssi")
            val kurssi = JSONObject(kurssiString)

            //haetaan tiedot ko. paikasta
            val lat = kurssi["lat"].toString().toDouble()
            val lng = kurssi["lng"].toString().toDouble()
            val coursename = kurssi["course"].toString()
            val address = kurssi["address"].toString()

            Toast.makeText(applicationContext, "$coursename osoite on $address", Toast.LENGTH_LONG).show()

            //luodaan muuttuja location joka on merkkijonot (URI) lat ja lng parsittuna
            val location = Uri.parse("geo:$lat,$lng")
            //luodaan muuttuja, jolla haetaan lokaation tiedot
            val mapIntent = Intent(Intent.ACTION_VIEW, location)

            val activities: List<ResolveInfo> = packageManager.queryIntentActivities(mapIntent, 0)
            //jos activities saa arvon, niin isIntentSafe saa arvoksi true -> aktiviteetti aloitetaan
            val isIntentSafe: Boolean = activities.isNotEmpty()

            if (isIntentSafe) {
                startActivity(mapIntent)
            } else {
                //avaa viesti-ikkunan käyttäjälle jos aktiviteetti ei onnistu
                Toast.makeText(this, "There is no activity to handle map intent!", Toast.LENGTH_LONG).show();
            }

        }
    }

    fun goToMain (view: View) {
        val intent = Intent(this, MainActivity::class.java);
        startActivity(intent);
    }



}
