package com.example.routes

import com.example.cache.InMemoryCache
import com.example.cache.TokenCache
import com.example.login.LoginResponseRemote
import com.example.login.RegisterReceiveRemote
import com.example.login.RegisterResponseRemote
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

            if (InMemoryCache.userList.map { it.login }.contains(receive.login)) {
                call.respond(HttpStatusCode.Conflict, "User already registered")
                return@post
            }

            if (receive.password != receive.passwordRepeat) {
                call.respond(HttpStatusCode.BadRequest, "Passwords must match")
                return@post
            }

            val token = UUID.randomUUID().toString()
            InMemoryCache.userList.add(receive)
            InMemoryCache.token.add(TokenCache(login = receive.login, token = token))

            call.respond(RegisterResponseRemote(token = token))
        }
    }
}