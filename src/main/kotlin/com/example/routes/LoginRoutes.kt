package com.example.routes

import com.example.cache.InMemoryCache
import com.example.cache.TokenCache
import com.example.login.LoginReceiveRemote
import com.example.login.LoginResponseRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

fun Route.loginRouting() {
    route("/login") {
        post {
            val receive = call.receive<LoginReceiveRemote>()
            val first = InMemoryCache.userList.find {it.login == receive.login}

            if (first == null) {
                call.respond(HttpStatusCode.BadRequest, "User not found")
            } else {
                if (first.password == receive.password) {
                    val token = UUID.randomUUID().toString()
                    InMemoryCache.token.add(TokenCache(login = receive.login, token = token))
                    call.respond(LoginResponseRemote(token))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid Password")
                }
            }
        }
    }
}