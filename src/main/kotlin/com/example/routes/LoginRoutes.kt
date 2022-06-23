package com.example.routes

import com.example.cache.InMemoryCache
import com.example.cache.TokenCache
import com.example.login.LoginController
import com.example.login.LoginReceiveRemote
import com.example.login.LoginResponseRemote
import com.example.utils.Const.INVALID_PASSWORD
import com.example.utils.Const.LOGIN_TOO_SHORT
import com.example.utils.Const.PASSWORD_TOO_SHORT
import com.example.utils.Const.USER_NOT_FOUND
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.loginRouting() {
    route("/login") {
        post {
            val loginController = LoginController(call)
            loginController.performLogin()
        }
    }
}