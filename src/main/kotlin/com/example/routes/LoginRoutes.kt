package com.example.routes

import com.example.cache.InMemoryCache
import com.example.cache.TokenCache
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
            val receive = call.receive<LoginReceiveRemote>()
            val first = InMemoryCache.userList.find {it.login == receive.login}

            if (receive.login.length < 3) {
                call.respond(HttpStatusCode.LengthRequired, LOGIN_TOO_SHORT)
                return@post
            } else if (receive.password.length < 5) {
                call.respond(HttpStatusCode.LengthRequired, PASSWORD_TOO_SHORT)
                return@post
            }

            if (first == null) {
                call.respond(HttpStatusCode.BadRequest, USER_NOT_FOUND)
            } else {
                if (first.password == receive.password) {
                    val token = UUID.randomUUID().toString()
                    InMemoryCache.token.add(TokenCache(login = receive.login, token = token))
                    call.respond(LoginResponseRemote(token))
                } else {
                    call.respond(HttpStatusCode.BadRequest, INVALID_PASSWORD)
                }
            }
        }
    }
}