package com.example.simplechat.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simplechat.dao.MessageDao

@Database(entities = [Message::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun messageDao(): MessageDao
}