package com.example.individualassignment.fragments.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.individualassignment.api.GamesRepository
import com.example.individualassignment.model.Game
import com.example.individualassignment.model.Screenshot
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UGFViewModel(application: Application): AndroidViewModel(application) {
    private val gamesRepository = GamesRepository()

    val games = MutableLiveData<List<Game>>()
    val error = MutableLiveData<String>()

    //For in GameDetailFragment
    val screenshots = MutableLiveData<List<Screenshot>>()
    val detailedGame = MutableLiveData<Game>()

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

    fun getGameDetails(game_id: Int) {
        gamesRepository.getGameDetails(game_id).enqueue(object: Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val results = response.body()
                val detailedGame = GsonBuilder().create().fromJson(results,Game::class.java)

                if (response.isSuccessful) this@UGFViewModel.detailedGame.value = detailedGame
                else error.value = "An error occured: ${response.errorBody().toString()}"
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                error.value = t.message
            }
        })
    }

    fun getScreenShots(game_id: Int) {
        gamesRepository.getScreenShots(game_id).enqueue(object: Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val results = response.body()?.get("results")

                val screenshots = GsonBuilder().create().fromJson(results,Array<Screenshot>::class.java).toList()

                if (response.isSuccessful) this@UGFViewModel.screenshots.value = screenshots
                else error.value = "An error occured: ${response.errorBody().toString()}"
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                error.value = t.message
            }
        })
    }
}