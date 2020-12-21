package com.saifur.githubusersearch.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.saifur.githubusersearch.data.local.AppDatabase
import com.saifur.githubusersearch.data.local.RoomDao
import com.saifur.githubusersearch.data.model.detailuser.UserData

class FavouriteUserProvider : ContentProvider() {

    private var db: AppDatabase? = null
    private var roomDao: RoomDao? = null

    private val USER = 1
    private val USER_ID = 2

    private val sURIMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        sURIMatcher.addURI(AUTHORITY, USER_TABLE, USER)
        sURIMatcher.addURI(AUTHORITY, "$USER_TABLE/#",
        USER_ID)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted = when(USER_ID){
            sURIMatcher.match(uri) -> roomDao?.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted!!
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added = when(USER){
            sURIMatcher.match(uri) -> roomDao?.addUser(UserData.fromContentValues(values))
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun onCreate(): Boolean {
        db = AppDatabase.getAppDataBase(context as Context)
        roomDao = db?.roomDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sURIMatcher.match(uri)){
            USER -> cursor = roomDao?.getUsers()
            USER_ID -> cursor = roomDao?.getUserDetail(uri.lastPathSegment!!.toInt())
            else -> cursor = null
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updated = when(USER_ID){
            sURIMatcher.match(uri) -> roomDao?.addUser(UserData.fromContentValues(values))
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated!!.toInt()
    }

    companion object{
        const val AUTHORITY = "com.saifur.githubusersearch.provider"
        const val USER_TABLE = "UserData"

        val CONTENT_URI = Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(USER_TABLE)
            .build()
    }
}