package com.example.routes

import com.example.cache.InMemoryCache
import com.example.cache.TokenCache
import com.example.login.RegisterController
import com.example.login.RegisterReceiveRemote
import com.example.login.RegisterResponseRemote
import com.example.utils.Const
import com.example.utils.Const.PASSWORDS_MUST_MATCH
import com.example.utils.Const.USER_ALREADY_REGISTERED
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.registerRouting() {
    route("/register") {
        post {
            val registerController = RegisterController(call)
            registerController.registerNewUser()
        }
    }
}