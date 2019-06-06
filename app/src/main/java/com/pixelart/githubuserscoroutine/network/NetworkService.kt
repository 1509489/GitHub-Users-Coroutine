package com.pixelart.githubuserscoroutine.network

import com.pixelart.githubuserscoroutine.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("users")
    suspend fun getUsers(@Query("since")since: Int? = null):Response<List<User>>
}