package com.example.simplechat.pubnub

import com.pubnub.api.PNConfiguration

object Config {

    fun getConfig(uid: String): PNConfiguration
    {
        return PNConfiguration().apply {
            subscribeKey = "sub-c-74120378-15f1-11eb-a3e5-1aef4acbe547"
            publishKey = "pub-c-8be3e895-0edb-4bcf-8581-723c128b7002"
            uuid = uid
        }
    }

}