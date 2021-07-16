package com.example.app

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.android.synthetic.main.app_toolbar.*

class AddUserActivity : AppCompatActivity() {
    private var userData: User? = null
    private var intentKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        this.init()
    }


    private fun init() {
        initToolbar()
        getIntentData()
        btnSave.setOnClickListener {
            if (intentKey == "Edit") {
                updateUser(userData)
            } else {
                saveUser()
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun initToolbar() {
        imgBack.setOnClickListener { finish() }
        tvTitle.text = "Add User"
        tvAdd.visibility = View.GONE
    }


    private fun saveUser() {

        val userName = edUserName.text.toString().trim()
        val userNumber = edUserNumber.text.toString().trim()
        val userEmail = edUserEmail.text.toString().trim()

        if (userName.isEmpty()) {
            edUserName.error = "UserName required"
            edUserName.requestFocus()
            return
        }

        if (userNumber.isEmpty()) {
            edUserNumber.error = "UserNumber required"
            edUserNumber.requestFocus()
            return
        }

        if (userEmail.isEmpty()) {
            edUserEmail.error = "UserEmail required"
            edUserEmail.requestFocus()
            return
        }


        class SaveUser : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                val user = User(0, userName, userNumber, userEmail)

                DatabaseClient.getInstance(this@AddUserActivity)
                    ?.getAppDatabase()
                    ?.userDao()
                    ?.insertUser(user)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Helper.showToast(this@AddUserActivity, "Saved")
                finish()
            }
        }

        val saveUser = SaveUser()
        saveUser.execute()
    }


    /**
     *  For Update
     */

    @SuppressLint("SetTextI18n")
    private fun getIntentData() {
        val intent = intent.extras
        if (intent != null) {
            intentKey = intent.getString("ForEdit", "")
            if (intentKey == "Edit") {
                btnSave.text = "Update"
                userData = MainActivity.userData
                setDataForUpdate(userData)
            }
        }
    }


    private fun setDataForUpdate(userData: User?) {
        edUserName.setText(userData?.userName)
        edUserNumber.setText(userData?.userNumber)
        edUserEmail.setText(userData?.userEmail)
    }


    private fun updateUser(userData: User?) {

        val userName = edUserName.text.toString().trim()
        val userNumber = edUserNumber.text.toString().trim()
        val userEmail = edUserEmail.text.toString().trim()

        if (userName.isEmpty()) {
            edUserName.error = "UserName required"
            edUserName.requestFocus()
            return
        }

        if (userNumber.isEmpty()) {
            edUserNumber.error = "UserNumber required"
            edUserNumber.requestFocus()
            return
        }

        if (userEmail.isEmpty()) {
            edUserEmail.error = "UserEmail required"
            edUserEmail.requestFocus()
            return
        }

        class UpdateUser : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                userData?.userName = userName
                userData?.userNumber = userNumber
                userData?.userEmail = userEmail

                DatabaseClient.getInstance(this@AddUserActivity)
                    ?.getAppDatabase()
                    ?.userDao()
                    ?.updateUser(userData!!)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                finish()
                Helper.showToast(this@AddUserActivity, "Updated")
            }
        }

        val updateUser = UpdateUser()
        updateUser.execute()
    }
}
