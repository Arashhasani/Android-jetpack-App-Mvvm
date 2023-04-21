package com.example.mvvm.data.remote

import com.example.mvvm.data.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/posts")
    suspend fun getAllPosts():Response<List<Post>>
}