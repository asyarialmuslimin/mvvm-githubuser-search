package com.saifur.consumerapp.data

data class UserData(
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
)