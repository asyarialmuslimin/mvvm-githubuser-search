package com.saifur.githubusersearch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saifur.githubusersearch.data.model.detailuser.UserData

@Database(entities = [UserData::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roomDao() : RoomDao

    companion object{
        var INSTANCE:AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if(INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "githubDB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}