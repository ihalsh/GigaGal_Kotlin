package com.udacity.gamedev.gigagal

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.udacity.gamedev.gigagal.entities.GigaGal
import com.udacity.gamedev.gigagal.entities.Platform
import ktx.graphics.use

class Level(private val gigaGal: GigaGal = GigaGal(),
            private val platforms: Array<Platform> = Array()) {

    init {
        // Add a test platform
        platforms.add(Platform(70f, 30f, 20f, 20f))
    }

    fun update(delta: Float) {
        // Update GigaGal
        gigaGal.update(delta)
    }

    fun render(batch: SpriteBatch, renderer: ShapeRenderer) {

        // Render all platforms in the platform array
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        for (platform in platforms) platform.render(renderer)
        renderer.end()

        // KTX way of using SpriteBatch
        batch.use { gigaGal.render(it) }
    }
}