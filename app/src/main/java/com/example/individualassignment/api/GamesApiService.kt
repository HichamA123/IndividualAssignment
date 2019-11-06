package com.example.individualassignment.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GamesApiService {

    @Headers("User-Agent: UniversityAssignment")
    //The GET method needed to retrieve games
    @GET("/api/games")
    fun getGames(@Query("dates") dates: String, @Query("ordering") ordering: String): Call<JsonObject>
}