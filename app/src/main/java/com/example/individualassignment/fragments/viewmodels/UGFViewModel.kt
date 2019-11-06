package com.example.individualassignment.fragments.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.individualassignment.api.GamesRepository
import com.example.individualassignment.model.Game
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UGFViewModel(application: Application): AndroidViewModel(application) {
    private val gamesRepository = GamesRepository()

    val games = MutableLiveData<List<Game>>()
    val error = MutableLiveData<String>()

    fun getGames(dates: String, ordering: String) {
        gamesRepository.getGames(dates, ordering).enqueue(object: Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val results = response.body()?.get("results")
                // puts the results in an array of type game
                val games = GsonBuilder().create().fromJson(results,Array<Game>::class.java).toList()

                if (response.isSuccessful) this@UGFViewModel.games.value = games
                else error.value = "An error occured: ${response.errorBody().toString()}"
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                error.value = t.message
            }
        })
    }
}