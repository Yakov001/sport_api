package com.example

import io.ktor.server.application.*
import com.example.plugins.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "192.168.50.75") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureSerialization()
}
