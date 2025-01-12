package com.sinaglife.shoping_cart.shared.config

import java.io.File
import java.util.*

object EnvLoader {
    fun loadEnv() {
        val projectRoot = System.getProperty("user.dir")
        val file = File(projectRoot + ".env")
        println("PROJECT ROOT" + projectRoot)
        if (file.exists()) {
            val props = Properties().apply { load(file.inputStream()) }
            props.forEach { (key, value) ->
                System.setProperty(key.toString(), value.toString())
            }
        }
    }
}