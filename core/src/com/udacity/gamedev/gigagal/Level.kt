package com.udacity.gamedev.gigagal

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.udacity.gamedev.gigagal.entities.GigaGal
import com.udacity.gamedev.gigagal.entities.Platform
import ktx.graphics.use

class Level(val gigaGal: GigaGal = GigaGal(),
            private val platforms: Array<Platform> = Array()) {

    init {
        // Add a test platforms
        platforms.add(Platform(70f, 30f, 20f, 20f))
        platforms.add(Platform(10f, 60f, 50f, 15f))
        platforms.add(Platform(20f, 120f, 40f, 30f))
        platforms.add(Platform(100f, 90f, 40f, 40f))
    }

    fun update(delta: Float) {
        // Update GigaGal
        gigaGal.update(delta, platforms)
    }

    fun render(batch: SpriteBatch) {

        // Render all platforms in the platform array
        for (platform in platforms)
            batch.use { platform.render(it) }

        // KTX way of using SpriteBatch
        batch.use { gigaGal.render(it) }
    }
}