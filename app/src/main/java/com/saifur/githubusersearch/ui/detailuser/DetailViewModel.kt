package com.saifur.githubusersearch.ui.detailuser

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saifur.githubusersearch.data.local.AppDatabase
import com.saifur.githubusersearch.data.local.RoomDao
import com.saifur.githubusersearch.data.model.detailuser.UserData
import com.saifur.githubusersearch.data.model.listuser.Item
import com.saifur.githubusersearch.data.remote.ApiService
import com.saifur.githubusersearch.helper.MappingHelper
import com.saifur.githubusersearch.provider.FavouriteUserProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private var db: AppDatabase? = null
    private var roomDao: RoomDao? = null

    private val apiService = ApiService().getService()

    var userData = MutableLiveData<UserData>()
    var followingData = MutableLiveData<ArrayList<Item>>()
    var followersData = MutableLiveData<ArrayList<Item>>()
    var favouriteState = MutableLiveData<Boolean>()

    private val compositeDisposable = CompositeDisposable()

    fun getRemoteData(username: String) {
        compositeDisposable.add(
            apiService.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userData.postValue(it)
                }, {
                    Log.d("DetailViewModel", "Error get Detail: ${it.message}")
                })
        )
    }

    fun setFollowing(username: String) {
        compositeDisposable.add(
            apiService.getFollowing(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    followingData.postValue(it as ArrayList<Item>?)
                }, {
                    Log.d("DetailViewModel", "Error get Following: ${it.message}")
                })
        )
    }

    fun setFollowers(username: String) {
        compositeDisposable.add(
            apiService.getFollowers(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    followersData.postValue(it as ArrayList<Item>?)
                }, {
                    Log.d("DetailViewModel", "Error get Followers: ${it.message}")
                })
        )
    }

    fun checkFavourite(id: Int, context: Context) {
        compositeDisposable.add(
            Observable.fromCallable {
                db = AppDatabase.getAppDataBase(context)
                roomDao = db?.roomDao()
                roomDao?.getUserDetail(id)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val favourites = MappingHelper.mapCursorToArrayList(it!!)
                    favouriteState.postValue(favourites.isNotEmpty())
                }, {
                    Log.d("DetailViewModel", "Error checkFavourite : ${it.message}")
                })
        )
    }

    fun getLocalData(id: Int, context: Context) {
        val GET_USER_URI = Uri.Builder().scheme("content")
            .authority(FavouriteUserProvider.AUTHORITY)
            .appendPath(FavouriteUserProvider.USER_TABLE)
            .appendPath(id.toString())
            .build()

        GlobalScope.launch(Dispatchers.Main) {
            val defferedUser = async(Dispatchers.IO) {
                val cursor = context.contentResolver.query(
                    GET_USER_URI,
                    null,
                    null,
                    null,
                    null
                )
                MappingHelper.mapCursorToArrayList(cursor!!)
            }
            val favouriteUsers = defferedUser.await()
            if (favouriteUsers.isNotEmpty()) {
                userData.postValue(favouriteUsers[0])
            }
        }

    }

    fun addFavourite(userData: UserData, context: Context) {
        compositeDisposable.add(
            Observable.fromCallable {
                db = AppDatabase.getAppDataBase(context)
                roomDao = db?.roomDao()

                roomDao?.addUser(userData)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    favouriteState.postValue(true)
                }, {
                    Log.d("DetailViewModel", "Error addFavourite : ${it.message}")
                })
        )
    }

    fun deleteFavourite(userData: UserData, context: Context) {
        compositeDisposable.add(
            Observable.fromCallable {
                db = AppDatabase.getAppDataBase(context)
                roomDao = db?.roomDao()

                roomDao?.deleteUser(userData)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    favouriteState.postValue(false)
                }, {
                    Log.d("DetailViewModel", "Error deleteFavourite : ${it.message}")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}