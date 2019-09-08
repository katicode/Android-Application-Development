package com.m9285.employeesfragmentsapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.employee_item.view.departmentTextView
import kotlinx.android.synthetic.main.employee_item.view.emailTextView
import kotlinx.android.synthetic.main.employee_item.view.nameTextView
import kotlinx.android.synthetic.main.employee_item.view.phoneTextView
import kotlinx.android.synthetic.main.employee_item.view.titleTextView
import kotlinx.android.synthetic.main.item_detail.view.*

/*
Create a new fragment class and name it to DetailFragment (extend it from android.support.v4.app.Fragment).
Here will display the selected employees data in the UI.
Here in the onCreateView() function a new rootView will be inflated from the item_detail.xml layout file.
An employee details will be show if there is a selection made in recycler view's adapter.
 */

class DetailFragment : Fragment() {

    // create view
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // get root view
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // show employee if there is a selection made in recycler view's adapter
        if (EmployeesAdapter.position != -1) {
            val employee = MainActivity.employees.getJSONObject(EmployeesAdapter.position)
            // show data in UI
            employee?.let {
                rootView.nameTextView.text = it.getString("lastName") + " " + it.getString("firstName")
                rootView.titleTextView.text = it.getString("title")
                rootView.emailTextView.text = it.getString("email")
                rootView.phoneTextView.text = it.getString("phone")
                rootView.departmentTextView.text = it.getString("department")
                /*val requestOptions = RequestOptions()
                requestOptions.override(500, 500)*/
                Glide.with(this).load(it.getString("image")).into(rootView.bigImage)
            }
        }
        // return view
        return rootView
    }
}