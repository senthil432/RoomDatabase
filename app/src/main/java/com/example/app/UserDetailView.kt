package com.example.app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_user_detail_view.*
import kotlinx.android.synthetic.main.app_toolbar.*

class UserDetailView : AppCompatActivity() {
    private var name = ""
    private var number = ""
    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail_view)

        initToolbar()
        getIntentData()
    }


    @SuppressLint("SetTextI18n")
    private fun initToolbar() {
        imgBack.setOnClickListener { finish() }
        tvTitle.text = "User Details"
        tvAdd.visibility = View.GONE
    }


    private fun getIntentData() {
        val intent = intent.extras
        if (intent != null) {
            name = intent.getString("Name", "")
            number = intent.getString("Number", "")
            email = intent.getString("Email", "")

            loadUserDetails()
        }
    }


    private fun loadUserDetails() {
        tvViewName.text = name
        tvViewNumber.text = number
        tvViewEmail.text = email
    }
}