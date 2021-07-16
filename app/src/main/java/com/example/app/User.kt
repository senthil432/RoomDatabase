package com.example.app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int,
    @ColumnInfo(name = "userName") var userName: String,
    @ColumnInfo(name = "userNumber") var userNumber: String,
    @ColumnInfo(name = "userEmail") var userEmail: String
)