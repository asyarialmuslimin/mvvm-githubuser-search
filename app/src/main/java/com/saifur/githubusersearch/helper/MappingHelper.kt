package com.saifur.githubusersearch.helper

import android.database.Cursor
import com.saifur.githubusersearch.data.model.detailuser.UserData

object MappingHelper {

    const val KEY_ID = "id"
    const val KEY_AVATAR = "avatar_url"
    const val KEY_BIO = "bio"
    const val KEY_COMPANY = "company"
    const val KEY_EMAIL = "email"
    const val KEY_FOLLOWER = "followers"
    const val KEY_FOLLOWING = "following"
    const val KEY_HTML_URL = "html_url"
    const val KEY_LOCATION = "location"
    const val KEY_USERNAME = "login"
    const val KEY_NAME = "name"
    const val KEY_PUBLICREPO = "public_repos"

    fun mapCursorToArrayList(userCursor:Cursor) : ArrayList<UserData>{
        val userList = arrayListOf<UserData>()

        userCursor.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(KEY_ID))
                val avatar = getString(getColumnIndexOrThrow(KEY_AVATAR))
                val bio = getString(getColumnIndexOrThrow(KEY_BIO))
                val company = getString(getColumnIndexOrThrow(KEY_COMPANY))
                val email = getString(getColumnIndexOrThrow(KEY_EMAIL))
                val followers = getInt(getColumnIndexOrThrow(KEY_FOLLOWER))
                val following = getInt(getColumnIndexOrThrow(KEY_FOLLOWING))
                val html_url = getString(getColumnIndexOrThrow(KEY_HTML_URL))
                val location = getString(getColumnIndexOrThrow(KEY_LOCATION))
                val username = getString(getColumnIndexOrThrow(KEY_USERNAME))
                val name = getString(getColumnIndexOrThrow(KEY_NAME))
                val publicrepo = getInt(getColumnIndexOrThrow(KEY_PUBLICREPO))

                userList.add(UserData(
                    id,
                    avatar,
                    bio,
                    company,
                    email,
                    followers,
                    following,
                    html_url,
                    location,
                    username,
                    name,
                    publicrepo
                ))
            }
        }
        return userList
    }
}