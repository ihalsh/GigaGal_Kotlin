package com.udacity.gamedev.gigagal

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.Viewport
import com.udacity.gamedev.gigagal.entities.*
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_COLLISION_RADIUS
import com.udacity.gamedev.gigagal.util.Constants.POWERUP_CENTER
import com.udacity.gamedev.gigagal.util.Utils
import ktx.graphics.use
import kotlin.text.Typography.bullet

class Level(private val platforms: Array<Platform> = Array(),
            val enemies: DelayedRemovalArray<Enemy> = DelayedRemovalArray(),
            private val bullets: DelayedRemovalArray<Bullet> = DelayedRemovalArray(),
            private val explosions: DelayedRemovalArray<Explosion> = DelayedRemovalArray(),
            val powerups: DelayedRemovalArray<Powerup> = DelayedRemovalArray(),
            val viewport: Viewport) {

    val gigaGal: GigaGal = GigaGal(level = this)

//    init {
//
//        // Add a test platforms
//        platforms.add(Platform(10f, 60f, 50f, 20f))
//        val powerupPlatform = Platform(20f, 120f, 60f, 20f)
//        platforms.add(powerupPlatform)
//        platforms.add(Platform(70f, 30f, 20f, 20f))
//        val enemyPlatform = Platform(80f, 60f, 70f, 30f)
//        platforms.add(enemyPlatform)
//        platforms.add(Platform(150f, 60f, 50f, 20f))
//        platforms.add(Platform(210f, 90f, 40f, 9f))
//        platforms.add(Platform(260f, 140f, 40f, 9f))
//        platforms.add(Platform(310f, 190f, 40f, 9f))
//        platforms.add(Platform(360f, 140f, 40f, 9f))
//        platforms.add(Platform(410f, 190f, 40f, 9f))
//        platforms.add(Platform(460f, 140f, 40f, 9f))
//        platforms.add(Platform(510f, 90f, 40f, 9f))
//        platforms.add(Platform(560f, 60f, 50f, 20f))
//
//        // Add an enemy sitting on enemyPlatform
//        enemies.add(Enemy(enemyPlatform))
//
//        // Add powerups
//        powerups.add(Powerup(Vector2(
//                powerupPlatform.left + powerupPlatform.width / 2 - POWERUP_CENTER.x,
//                powerupPlatform.top + POWERUP_CENTER.y
//        )))
//    }

    fun update(delta: Float) {
        // Update GigaGal
        gigaGal.update(delta, platforms)

        // Update the enemies
        enemies.begin()
        for (enemy in enemies) {
            with(enemy) {
                update(delta)
                if (healthCounter <1 ) {
                    enemies.removeValue(this, true)
                    explosions.add(Explosion(position.add(
                            ENEMY_COLLISION_RADIUS,
                            ENEMY_COLLISION_RADIUS
                    ), TimeUtils.nanoTime()))
                }
            }
        }
        enemies.end()

        // Update the bullets
        enemies.begin()
        for (bullet in bullets) {
            with(bullet) {
                update(delta)
                if (!isActive) bullets.removeValue(this, true)
            }
        }
        enemies.end()

        // Remove any explosions that are finished
        explosions.begin()
        for (explosion in explosions)
            if (explosion.isFinished()) explosions.removeValue(explosion, true)
        explosions.end()
    }

    fun render(batch: SpriteBatch) {

        // Render all platforms in the platform array
        for (platform in platforms)
            batch.use { platform.render(it) }

        // KTX way of using SpriteBatch
        batch.use { gigaGal.render(it) }

        // Render the enemies
        for (enemy in enemies) batch.use { enemy.render(it) }

        // Render powerups
        for (powerup in powerups) batch.use { powerup.render(it) }

        // Render the bullets
        for (bullet in bullets) batch.use { bullet.render(it) }

        // Render the explosions
        for (explosion in explosions) batch.use { explosion.render(it) }
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

    fun spawnExplosion(position: Vector2) {
        explosions.add(Explosion(position, TimeUtils.nanoTime()))
    }
}