package com.example.routes

import com.example.cache.InMemoryCache
import com.example.cache.TokenCache
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
            val receive = call.receive<RegisterReceiveRemote>()

            if (receive.login.length < 3) {
                call.respond(HttpStatusCode.LengthRequired, Const.LOGIN_TOO_SHORT)
                return@post
            } else if (receive.password.length < 5) {
                call.respond(HttpStatusCode.LengthRequired, Const.PASSWORD_TOO_SHORT)
                return@post
            }

            if (InMemoryCache.userList.map { it.login }.contains(receive.login)) {
                call.respond(HttpStatusCode.Conflict, USER_ALREADY_REGISTERED)
                return@post
            }

            if (receive.password != receive.passwordRepeat) {
                call.respond(HttpStatusCode.BadRequest, PASSWORDS_MUST_MATCH)
                return@post
            }

            val token = UUID.randomUUID().toString()
            InMemoryCache.userList.add(receive)
            InMemoryCache.token.add(TokenCache(login = receive.login, token = token))

            call.respond(RegisterResponseRemote(token = token))
        }
    }
}