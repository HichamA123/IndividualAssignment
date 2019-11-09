package com.example.individualassignment.api

class GamesRepository {

    private val gamesApi: GamesApiService = GamesApi.createApi()

    fun getGames(dates: String, ordering: String) = gamesApi.getGames(dates, ordering)

    fun getGameDetails(game_id: Int) = gamesApi.getGameDetails(game_id)

    fun getScreenShots(game_id: Int) = gamesApi.getScreenShots(game_id)


}