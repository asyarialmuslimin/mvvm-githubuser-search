package com.saifur.githubusersearch.data.local

import android.database.Cursor
import androidx.room.*
import com.saifur.githubusersearch.data.model.detailuser.UserData

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user:UserData) : Long

    @Delete
    fun deleteUser(user:UserData)

    @Query("DELETE FROM UserData WHERE id == :id")
    fun deleteById(id:String) : Int

    @Query("SELECT * FROM UserData WHERE id == :id")
    fun getUserDetail(id:Int) : Cursor

    @Query("SELECT * FROM UserData WHERE login LIKE :name")
    fun searchUsers(name:String) : Cursor

    @Query("SELECT * FROM UserData")
    fun getUsers() : Cursor
}