package com.example.simplechat.ui.main

import androidx.lifecycle.*
import com.example.simplechat.dao.MessageDao
import com.example.simplechat.entity.Message
import com.example.simplechat.entity.UserChanel
import com.example.simplechat.pubnub.Config
import com.example.simplechat.utils.Preferences
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class MainViewModel(
    private val preferences: Preferences,
    private val messageDao: MessageDao
) : ViewModel() {

    private val uid = preferences.getUid()
    var userName = preferences.getUserName()
    private val pubnub = PubNub(Config.getConfig(uid.toString()))
    val messageList = messageDao.getAll().asLiveData()
    var userNameIsSet = MutableLiveData<Boolean>().apply { value = userName.isNotEmpty() }
    val messageText = MutableLiveData<String>().apply { value = "" }

    init {
        initPubNub()
    }

    private fun initPubNub()
    {
        pubnub.addListener(object : SubscribeCallback() {
            override fun status(pubnub: PubNub, pnStatus: PNStatus) {

            }

            override fun message(pubnub: PubNub, pnMessageResult: PNMessageResult) {
                super.message(pubnub, pnMessageResult)

                val message = Message.fromJson(pnMessageResult.message.asJsonObject)
                insertMessageForOther(message)
            }

        })

        pubnub.subscribe(listOf(UserChanel.getChanel()), emptyList(), true)

    }

    fun sendMessage()
    {
        if (messageText.value?.isEmpty()!!)
            return

        val message = Message(messageText.value ?: "", userName, uid.toString())
            .apply { time = System.currentTimeMillis() }

        insertMessage(message)

        pubnub.publish(UserChanel.getChanel(), message.toJson()).async { result, status ->
            if (status.error) {
                status.exception?.printStackTrace()
            } else {
                messageText.value = ""
            }
        }
    }

    fun saveUserName()
    {
        preferences.saveUserName(userName)
        userNameIsSet.value = userName.isNotEmpty()
    }

    private fun insertMessageForOther(message: Message)
    {
        val messageUid = UUID.fromString(message.userUid)

        if (messageUid != uid)
            insertMessage(message)
    }

    private fun insertMessage(message: Message)
    {
        viewModelScope.launch(Dispatchers.IO) {
            messageDao.insert(message)
        }
    }

}