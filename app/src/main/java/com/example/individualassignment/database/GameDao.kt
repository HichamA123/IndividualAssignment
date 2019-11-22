package com.example.individualassignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.individualassignment.model.Game


@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Query("SELECT * FROM game_table WHERE id = :id")
    suspend fun getGame(id: Int): List<Game>

    @Insert
    suspend fun insertGame(game: Game)
}