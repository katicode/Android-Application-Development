package com.m9285.golfapplication

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.golf_item.view.*
import org.json.JSONArray
import org.json.JSONObject

// Adapter, used in RecyclerView in MainActivity
class GolfAdapter(private val kurssit: JSONArray) : RecyclerView.Adapter<GolfAdapter.ViewHolder>() {

    // create UI View Holder from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GolfAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.golf_item, parent, false)
        return ViewHolder(view)
    }

    // return item count in courses
    override fun getItemCount(): Int = kurssit.length()

    // bind data to UI View Holder
    override fun onBindViewHolder(holder: GolfAdapter.ViewHolder, position: Int) {
        // employee to bind UI
        val kurssi: JSONObject = kurssit.getJSONObject(position)
        // name
        holder.courseTextView.text = kurssi["course"].toString()
        holder.addressTextView.text = kurssi["address"].toString()
        holder.phoneTextView.text = kurssi["phone"].toString()
        holder.emailTextView.text = kurssi["email"].toString()
        //kuvien käsittely
        val url = kurssi["image"].toString()
        val kokoUrl = "http://ptm.fi/materials/golfcourses/" + url
        Glide.with(holder.imageView.context).load(kokoUrl).into(holder.imageView)

    }

    // View Holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //tekstikentät
        val courseTextView: TextView = view.courseTextView
        val addressTextView: TextView = view.addressTextView
        val phoneTextView: TextView = view.phoneTextView
        val emailTextView: TextView = view.emailTextView
        //kuva
        val imageView: ImageView = view.placeImage


        //add a item click listener
        //kun jotain kohtaa klikataan, viedään tiedot CourseActivitylle
        init {
            itemView.setOnClickListener {
                //Toast.makeText(view.context,"Course is ${courseTextView.text}, adapter position = $adapterPosition", Toast.LENGTH_LONG).show()
                // create an explicit intent
                val intent = Intent(view.context, CourseActivity::class.java)
                // add selected course json as a string data
                intent.putExtra("kurssi",kurssit[adapterPosition].toString())
                // start a new activity
                view.context.startActivity(intent)
            }
        }

    }
}

