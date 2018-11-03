package com.udacity.gamedev.gigagal

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.badlogic.gdx.utils.viewport.Viewport
import com.udacity.gamedev.gigagal.entities.Bullet
import com.udacity.gamedev.gigagal.entities.Enemy
import com.udacity.gamedev.gigagal.entities.GigaGal
import com.udacity.gamedev.gigagal.entities.Platform
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Utils
import ktx.graphics.use

class Level(private val platforms: Array<Platform> = Array(),
            val enemies: Array<Enemy> = DelayedRemovalArray(),
            private val bullets: Array<Bullet> = DelayedRemovalArray(),
            val viewport: Viewport) {

    val gigaGal: GigaGal = GigaGal(level = this)

    init {

        // Add a test platforms
        platforms.add(Platform(10f, 60f, 50f, 20f))
        platforms.add(Platform(20f, 120f, 60f, 20f))
        platforms.add(Platform(70f, 30f, 20f, 20f))
        val enemyPlatform = Platform(80f, 60f, 70f, 30f)
        platforms.add(enemyPlatform)
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
        for (enemy in enemies) {
            with(enemy) {
                update(delta)
                if (healthCounter <1 ) enemies.removeValue(this, true)
            }
        }

        // Update the bullets
        for (bullet in bullets) {
            with(bullet) {
                update(delta)
                if (!isActive) bullets.removeValue(this, true)
            }
        }
    }

    fun render(batch: SpriteBatch) {

        // Render all platforms in the platform array
        for (platform in platforms)
            batch.use { platform.render(it) }

        // KTX way of using SpriteBatch
        batch.use { gigaGal.render(it) }

        // Render the enemies
        for (enemy in enemies) batch.use { enemy.render(it) }

        // Render the bullets
        for (bullet in bullets) batch.use { bullet.render(it) }
    }

    fun spawnBullet(position: Vector2, direction: Constants.Facing, level: Level) {
        when (direction) {
            Constants.Facing.RIGHT -> bullets.add(Bullet(Vector2(
                    position.x + 10,
                    position.y - 9), direction, level))
            Constants.Facing.LEFT -> bullets.add(Bullet(Vector2(
                    position.x - 14.5f,
                    position.y - 9), direction, level))
        }
    }
}