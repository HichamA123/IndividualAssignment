package com.example.individualassignment.api

class GamesRepository {

    private val gamesApi: GamesApiService = GamesApi.createApi()

    fun getGames(dates: String, ordering: String) = gamesApi.getGames(dates, ordering)


}