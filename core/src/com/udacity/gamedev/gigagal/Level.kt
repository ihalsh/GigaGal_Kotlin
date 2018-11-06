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
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_KILL_SCORE
import com.udacity.gamedev.gigagal.util.Constants.EXIT_PORTAL_CENTER
import ktx.graphics.use
import ktx.log.info

class Level(val platforms: Array<Platform> = Array(),
            val enemies: DelayedRemovalArray<Enemy> = DelayedRemovalArray(),
            private val bullets: DelayedRemovalArray<Bullet> = DelayedRemovalArray(),
            private val explosions: DelayedRemovalArray<Explosion> = DelayedRemovalArray(),
            val powerups: DelayedRemovalArray<Powerup> = DelayedRemovalArray(),
            val exitPortal: ExitPortal = ExitPortal(),
            var score: Int = 0,
            private var gameOver: Boolean = false,
            var victory: Boolean = false,
            val viewport: Viewport) {

    val gigaGal: GigaGal = GigaGal(level = this)

    fun update(delta: Float) {

        // If GigaGal is touching the exit portal, set victory to true
        if (gigaGal.gigaGalBounds.contains(Vector2().set(
                        exitPortal.position.x + EXIT_PORTAL_CENTER.x,
                        exitPortal.position.y + EXIT_PORTAL_CENTER.y))) {
            victory = true
        }

        if (!gameOver && !victory) {

            // Update GigaGal
            gigaGal.update(delta, platforms)

            // Update the enemies
            enemies.begin()
            for (enemy in enemies) {
                with(enemy) {
                    update(delta)
                    if (healthCounter < 1) {
                        enemies.removeValue(this, true)
                        // Add the ENEMY_KILL_SCORE to the score
                        score += ENEMY_KILL_SCORE
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
    }

    fun render(batch: SpriteBatch) {

            batch.use {
                // Render all platforms in the platform array
                for (platform in platforms) platform.render(it)
                // Render the exit portal
                exitPortal.render(it)
                // Render the enemies
                for (enemy in enemies) enemy.render(it)
                // Render powerups
                for (powerup in powerups) powerup.render(it)
                // Render the bullets
                for (bullet in bullets) bullet.render(it)
                // Render the explosions
                for (explosion in explosions) explosion.render(it)
                // Render GigaGal
                gigaGal.render(it)
            }
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

    fun spawnExplosion(position: Vector2) = explosions.add(Explosion(position = position))
}