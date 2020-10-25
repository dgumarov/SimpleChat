package com.example.simplechat.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class Preferences(context: Context) {
    private val preferenceFileName = "Settings"

    private val preferences: SharedPreferences = context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    companion object Keys {
        const val UID = "uid"
        const val USERNAME = "userName"
    }

    fun saveUid(uid: UUID)
    {
        editor.putString(UID, uid.toString()).commit()
    }

    fun getUid(): UUID
    {
        val current = preferences.getString(UID, "")
        if (current.isNullOrEmpty())
        {
            val newUid = UUID.randomUUID()
            saveUid(newUid)
            return newUid
        }

        return UUID.fromString(current)
    }

    fun saveUserName(userName: String)
    {
        editor.putString(USERNAME, userName).commit()
    }

    fun getUserName(): String
    {
        return preferences.getString(USERNAME, "") ?: ""
    }
}