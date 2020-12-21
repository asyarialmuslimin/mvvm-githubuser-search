package com.saifur.consumerapp

import android.annotation.SuppressLint
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.saifur.consumerapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var favAdapter: UserFavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        favAdapter = UserFavAdapter()

        Log.d("ContentURI", CONTENT_URI.toString())

        rview.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = favAdapter
        }

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                getData()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if(savedInstanceState == null){
            getData()
        }
    }

    @SuppressLint("Recycle")
    private fun getData(){
        GlobalScope.launch(Dispatchers.Main) {
            val defferedUser = async(Dispatchers.IO){
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor!!)
            }
            val favouriteUsers = defferedUser.await()
            if(favouriteUsers.isNotEmpty()){
                favAdapter.setData(favouriteUsers)
            }else{
                Toast.makeText(this@MainActivity, "Data Kosong", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object{
        const val AUTHORITY = "com.saifur.githubusersearch.provider"
        const val USER_TABLE = "UserData"

        val CONTENT_URI: Uri = Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(USER_TABLE)
            .build()
    }
}