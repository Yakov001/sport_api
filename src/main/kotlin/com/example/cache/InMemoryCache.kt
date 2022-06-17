package com.example.cache

import com.example.login.RegisterReceiveRemote

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {
    val userList: MutableList<RegisterReceiveRemote>
    = mutableListOf(
        RegisterReceiveRemote("yakov", "aaa", "aaa")
    )
    val token: MutableList<TokenCache> = mutableListOf()
}