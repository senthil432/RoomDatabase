package com.example.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.app.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_toolbar.*

class MainActivity : AppCompatActivity(), UserAdapter.ItemClicked {
    private var userAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.initialize()
    }


    private fun initialize() {
        initToolbar()
    }


    @SuppressLint("SetTextI18n")
    private fun initToolbar() {
        imgBack.setOnClickListener { finish() }
        tvTitle.text = "Users"
        tvAdd.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()
        getUsers()
        userAdapter?.notifyDataSetChanged()
    }


    private fun getUsers() {

        class GetUsers : AsyncTask<Void, Void, List<User>>() {
            override fun doInBackground(vararg p0: Void?): List<User> {

                val userList: List<User> = DatabaseClient.getInstance(this@MainActivity)
                    ?.getAppDatabase()
                    ?.userDao()
                    ?.getAllUsers()!!

                return userList
            }

            override fun onPostExecute(result: List<User>?) {
                super.onPostExecute(result)

                if (!result.isNullOrEmpty()) {
                    Helper.showToast(this@MainActivity, "User Listed Successfully")
                }

                loadUserList(result!!)
            }
        }

        val getUser = GetUsers()
        getUser.execute()
    }


    private fun loadUserList(userList: List<User>) {
        if (!userList.isNullOrEmpty()) {
            tvNoUserFound.visibility = View.GONE
            recyclerUserList.visibility = View.VISIBLE
            userAdapter = UserAdapter(this, userList, this)
            recyclerUserList.adapter = userAdapter
            userAdapter?.notifyDataSetChanged()
        } else {
            recyclerUserList.visibility = View.GONE
            tvNoUserFound.visibility = View.VISIBLE
            userAdapter?.notifyDataSetChanged()
        }
    }


    override fun itemClick(flag: Boolean?, value: String) {
        if (flag!!) {
            when (value) {
                "Update" -> {
                    val i = Intent(this, AddUserActivity::class.java)
                    i.putExtra("ForEdit", "Edit")
                    startActivity(i)
                }
                "Delete" -> {
                    deleteAlert()
                }
                "View" -> {
                    val i = Intent(this, UserDetailView::class.java)
                    i.putExtra("Name", userData?.userName)
                    i.putExtra("Number", userData?.userNumber)
                    i.putExtra("Email", userData?.userEmail)
                    startActivity(i)
                }
            }
        }
    }


    private fun deleteAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you wanna delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, i ->
                deleteUser(userData!!)
            }
            .setNegativeButton("No") { dialogInterface, i ->
                dialogInterface.cancel()
            }
        val alert = builder.create()
        alert.setTitle("Delete User")
        alert.show()
    }


    companion object {
        var userData: User? = null
    }


    /**
     * For Delete
     */
    private fun deleteUser(user: User) {
        class DeleteUser : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                DatabaseClient.getInstance(this@MainActivity)
                    ?.getAppDatabase()
                    ?.userDao()
                    ?.deleteUser(user)

                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Helper.showToast(this@MainActivity, "Deleted")
                getUsers()
            }
        }

        val deleteUser = DeleteUser()
        deleteUser.execute()
    }
}
