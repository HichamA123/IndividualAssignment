package com.example.individualassignment.database

import android.content.Context
import com.example.individualassignment.model.Game

class GameRepository(context: Context) {
    private var gameDao: GameDao

    init {
        val gameRoomDatabase = GameRoomDatabase.getDatabase(context)
        gameDao = gameRoomDatabase!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> = gameDao.getAllGames()

    suspend fun getGame(id: Int): List<Game> = gameDao.getGame(id)

    suspend fun insertGame(game: Game) = gameDao.insertGame(game)
}