package com.saifur.githubusersearch.data.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    private fun okHttpClient() : OkHttpClient{
        val builder = OkHttpClient().newBuilder();
        val client = OkHttpClient.Builder().apply {
            builder.addInterceptor { chain ->
                val request: Request =
                    chain.request().newBuilder().addHeader("Authorization", "token <YOUR GITHUB API TOKEN>").build()
                chain.proceed(request)
            }
        }
        return client.build()
    }

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService() = getRetrofit().create(Endpoint::class.java)
}
