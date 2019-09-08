package com.m9285.employeesapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_employee.*
import org.json.JSONObject

class EmployeeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        // get data from intent
        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val employeeString = bundle!!.getString("employee")
            val employee = JSONObject(employeeString)
            //val firstname = employee["firstName"]
            // val lastname = employee["lastName"]
            // Toast.makeText(this, "Name is $firstname $lastname", Toast.LENGTH_LONG).show()

            //tekstien tuonti
            nameTextView2.text = employee["lastName"].toString() + " " + employee["firstName"].toString()
            titleTextView2.text = employee["title"].toString()
            emailTextView2.text = employee["email"].toString()
            phoneTextView2.text = employee["phone"].toString()
            departmentTextView2.text = employee["department"].toString()
            //kuvan tuonti
            val url = employee["image"].toString()
            Glide.with(imageView2.context).load(url).into(imageView2)
        }

    }
}
