package com.m9285.golfcourseswishlist

import android.content.Context

class Place {
    var name: String? = null
    var image: String? = null

    //palauttaa resource id:n
    //metodia käytetään oikean kuvan saamiseksi card viewiin
    fun getImageResourceId(context: Context): Int {
        return context.resources.getIdentifier(this.image,"drawable", context.packageName)
    }
}