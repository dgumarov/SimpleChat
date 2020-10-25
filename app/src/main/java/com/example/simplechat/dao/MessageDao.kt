package com.example.simplechat.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.simplechat.entity.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM Message ORDER BY id DESC LIMIT 100")
    fun getAll(): Flow<List<Message>>

    @Insert
    suspend fun insert(message: Message)

}