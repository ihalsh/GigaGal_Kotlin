package com.udacity.gamedev.gigagal

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.udacity.gamedev.gigagal.entities.Enemy
import com.udacity.gamedev.gigagal.entities.GigaGal
import com.udacity.gamedev.gigagal.entities.Platform
import ktx.graphics.use

class Level(val gigaGal: GigaGal = GigaGal(),
            private val platforms: Array<Platform> = Array(),
            private val enemies: Array<Enemy> = DelayedRemovalArray(),
            private val enemy: Enemy = Enemy()) {

    init {
        // Add a test platforms
        platforms.add(Platform(10f, 60f, 50f, 20f))
        platforms.add(Platform(20f, 120f, 40f, 30f))
        val enemyPlatform = Platform(70f, 30f, 20f, 20f)
        platforms.add(enemyPlatform)
        platforms.add(Platform(100f, 90f, 40f, 40f))
        platforms.add(Platform(150f, 60f, 50f, 20f))
        platforms.add(Platform(210f, 90f, 40f, 9f))
        platforms.add(Platform(260f, 140f, 40f, 9f))
        platforms.add(Platform(310f, 190f, 40f, 9f))
        platforms.add(Platform(360f, 140f, 40f, 9f))
        platforms.add(Platform(410f, 190f, 40f, 9f))
        platforms.add(Platform(460f, 140f, 40f, 9f))
        platforms.add(Platform(510f, 90f, 40f, 9f))
        platforms.add(Platform(560f, 60f, 50f, 20f))

        // Add an enemy sitting on enemyPlatform
        enemies.add(Enemy(enemyPlatform))
    }

    fun update(delta: Float) {
        // Update GigaGal
        gigaGal.update(delta, platforms)

        // Update the enemies
        for (enemy in enemies) enemy.update(delta)
    }

    fun render(batch: SpriteBatch) {

        // Render all platforms in the platform array
        for (platform in platforms)
            batch.use { platform.render(it) }

        // KTX way of using SpriteBatch
        batch.use { gigaGal.render(it)
        }

        // Render the enemies
        for (enemy in enemies) batch.use { enemy.render(it) }
    }
}