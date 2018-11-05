package com.udacity.gamedev.gigagal

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.Viewport
import com.udacity.gamedev.gigagal.entities.*
import com.udacity.gamedev.gigagal.overlays.GigaGalHud
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_COLLISION_RADIUS
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_KILL_SCORE
import com.udacity.gamedev.gigagal.util.Constants.POWERUP_CENTER
import com.udacity.gamedev.gigagal.util.Utils
import ktx.graphics.use
import kotlin.text.Typography.bullet

class Level(val platforms: Array<Platform> = Array(),
            val enemies: DelayedRemovalArray<Enemy> = DelayedRemovalArray(),
            private val bullets: DelayedRemovalArray<Bullet> = DelayedRemovalArray(),
            private val explosions: DelayedRemovalArray<Explosion> = DelayedRemovalArray(),
            val powerups: DelayedRemovalArray<Powerup> = DelayedRemovalArray(),
            val exitPortal: ExitPortal = ExitPortal(),
            var score:Int = 0,
            val viewport: Viewport) {

    val gigaGal: GigaGal = GigaGal(level = this)

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

        // Render the exit portal
        batch.use { exitPortal.render(it) }

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