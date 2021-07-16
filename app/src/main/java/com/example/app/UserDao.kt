package com.example.app

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM  user_table")
    fun getAllUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)
}