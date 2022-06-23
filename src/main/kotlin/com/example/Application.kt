package com.example

import io.ktor.server.application.*
import com.example.plugins.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    val config = HikariConfig("hikari.properties")
    val datasource = HikariDataSource(config)
    Database.connect(datasource)
    // embeddedServer(Netty, port = 8080, host = "192.168.50.75")
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureSerialization()
}
