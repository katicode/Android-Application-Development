package com.m9285.appwidgetexercise

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

//  List Adapter
class ListAdapter (var List: MutableList<ListItem>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    // create UI View Holder from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    // return list size
    override fun getItemCount(): Int = List.size

    // bind data to UI View Holder
    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        // item to bind UI
        val item: ListItem = List[position]
        // goodthing
        holder.tekstitahan.text = item.asia
        //holder.pvmtahan.text = item.pvm.toString()
    }

    // View Holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tekstitahan: TextView = view.tekstitahan
        //val pvmtahan: TextView = view.pvmtahan
    }

    // update data inside adapter
    fun update(newList: MutableList<ListItem>) {
        List = newList
        notifyDataSetChanged()
    }
}