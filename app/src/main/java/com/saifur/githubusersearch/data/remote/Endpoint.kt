package com.saifur.githubusersearch.data.remote

import com.saifur.githubusersearch.data.model.detailuser.UserData
import com.saifur.githubusersearch.data.model.listuser.Item
import com.saifur.githubusersearch.data.model.listuser.Users
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {

    @GET("search/users")
    fun searchUser(@Query("q") username: String): Single<Users>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Single<UserData>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Single<List<Item>>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Single<List<Item>>

    @GET("search/users")
    fun getTopUsers(
        @Query("q") query: String,
        @Query("ref") ref: String,
        @Query("s") s: String,
        @Query("type") type: String
    ): Single<Users>

}