package com.example.simplechat.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import java.lang.Exception

@Entity
data class Message (
    val messageText: String,
    val userName: String,
    val userUid: String
)
{
    var time: Long = System.currentTimeMillis()
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        fun fromJson(jsonObject: JsonObject): Message
        {
            try {
                val text = jsonObject["message"].asString
                val userName = jsonObject["userName"].asString
                val userUid = jsonObject["userUid"].asString

                return Message(text, userName, userUid)
            } catch (e: Exception)
            {
                e.printStackTrace()
            }

            return Message("", "", "")
        }

        fun timeFromString(strTime: String): Long
        {
            return try {
                strTime.toLong()
            }catch (e: Exception) {
                e.printStackTrace()
                System.currentTimeMillis()
            }
        }
    }

    fun toJson(): JsonObject
    {
        return JsonObject().apply {
            addProperty("message", messageText)
            addProperty("userName", userName)
            addProperty("userUid", userUid)
        }
    }


}