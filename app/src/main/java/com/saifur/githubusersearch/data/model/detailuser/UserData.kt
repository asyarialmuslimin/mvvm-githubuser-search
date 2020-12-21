package com.saifur.githubusersearch.data.model.detailuser

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saifur.githubusersearch.helper.MappingHelper

@Entity
data class UserData(
    @PrimaryKey
    val id: Int,
    val avatar_url: String?,
    val bio: String?,
    val company: String?,
    val email: String?,
    val followers: Int?,
    val following: Int?,
    val html_url: String?,
    val location: String?,
    val login: String?,
    val name: String?,
    val public_repos: Int?,
) {
    companion object {
        fun fromContentValues(value: ContentValues?): UserData {
            return UserData(
                value!!.getAsInteger(MappingHelper.KEY_ID),
                value.getAsString(MappingHelper.KEY_AVATAR),
                value.getAsString(MappingHelper.KEY_BIO),
                value.getAsString(MappingHelper.KEY_COMPANY),
                value.getAsString(MappingHelper.KEY_EMAIL),
                value.getAsInteger(MappingHelper.KEY_FOLLOWER),
                value.getAsInteger(MappingHelper.KEY_FOLLOWING),
                value.getAsString(MappingHelper.KEY_HTML_URL),
                value.getAsString(MappingHelper.KEY_LOCATION),
                value.getAsString(MappingHelper.KEY_USERNAME),
                value.getAsString(MappingHelper.KEY_NAME),
                value.getAsInteger(MappingHelper.KEY_PUBLICREPO)
            )
        }
    }
}