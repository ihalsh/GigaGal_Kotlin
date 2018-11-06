package com.udacity.gamedev.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.udacity.gamedev.gigagal.GigaGalGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = 1200
        config.height = 800
        LwjglApplication(GigaGalGame(), config)
    }
}
