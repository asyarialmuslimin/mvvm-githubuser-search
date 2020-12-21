package com.saifur.githubusersearch.ui.listfavourite

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saifur.githubusersearch.data.local.AppDatabase
import com.saifur.githubusersearch.data.model.detailuser.UserData
import com.saifur.githubusersearch.helper.MappingHelper
import com.saifur.githubusersearch.provider.FavouriteUserProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ListFavouriteViewModel : ViewModel() {

    val listFavourite = MutableLiveData<List<UserData>>()

    @SuppressLint("CheckResult")
    fun searchUser(context: Context, username:String){

        Observable.fromCallable {
            val db = AppDatabase.getAppDataBase(context)
            val roomDao = db?.roomDao()
            roomDao?.searchUsers("%$username%")
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val favourites = MappingHelper.mapCursorToArrayList(it!!)
                listFavourite.postValue(favourites)
            },{
                Log.d("DetailViewModel", "Error showUserDetail : ${it.message}")
            })
    }

    fun getListFavourite(context: Context) {
        GlobalScope.launch(Dispatchers.Main) {
            val defferedUser = async(Dispatchers.IO) {
                val cursor = context.contentResolver.query(
                    FavouriteUserProvider.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                )
                MappingHelper.mapCursorToArrayList(cursor!!)
            }
            val favouriteUsers = defferedUser.await()
            if (favouriteUsers.isNotEmpty()) {
                listFavourite.postValue(favouriteUsers)
            }
        }
    }
}