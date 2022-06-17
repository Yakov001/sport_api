package com.example.login

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceiveRemote(
    val login: String,
    val password: String,
    val passwordRepeat: String
)

@Serializable
data class RegisterResponseRemote(
    val token: String
)