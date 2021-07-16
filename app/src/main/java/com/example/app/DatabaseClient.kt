package com.example.app

import android.content.Context
import androidx.room.Room

open class DatabaseClient private constructor(context: Context) {
    //our app database object
    private var userDataBase: UserDataBase? = null

    init {
        userDataBase = Room.databaseBuilder(context, UserDataBase::class.java, "user_table").build()
    }

    companion object {
        private var mInstance: DatabaseClient? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance = DatabaseClient(context)
            }
            return mInstance
        }
    }


    fun getAppDatabase(): UserDataBase? {
        return userDataBase
    }
}