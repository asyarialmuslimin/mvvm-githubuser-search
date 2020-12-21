package com.saifur.githubusersearch.ui.listuser

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saifur.githubusersearch.data.model.listuser.Item
import com.saifur.githubusersearch.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserViewModel : ViewModel() {
    private var apiService = ApiService().getService()
    val listUsers = MutableLiveData<ArrayList<Item>>()
    val compositeDisposable = CompositeDisposable()

    fun searchUsers(username:String){
        compositeDisposable.add(
            apiService.searchUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listUsers.postValue(it.items as ArrayList<Item>?)
                },{
                    Log.d("UserViewModel", "Error get Users: ${it.message}")
                })
        )
    }

    fun getPopularUser(){
        compositeDisposable.add(
            apiService.getTopUsers(
                "repos:followers:<1000",
                "searchresults",
                "followers",
                "Users"
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("ItemSize", it.items.size.toString())
                    listUsers.postValue(it.items as ArrayList<Item>?)
                },{
                    Log.d("UserViewModel", "error get popular user : ${it.message}")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}