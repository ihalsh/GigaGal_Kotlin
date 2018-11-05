package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.udacity.gamedev.gigagal.Level
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_COLLISION_RADIUS
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_HIT_SCORE
import com.udacity.gamedev.gigagal.util.Constants.Facing.LEFT
import com.udacity.gamedev.gigagal.util.Constants.Facing.RIGHT
import com.udacity.gamedev.gigagal.util.Utils.Companion.drawBatch

class Bullet(private val position: Vector2 = Vector2(),
             private val direction: Constants.Facing = RIGHT,
             private val level: Level,
             var isActive: Boolean = true) {

    fun update(delta: Float) {

        when (direction) {
            RIGHT -> position.x += delta * Constants.BULLET_SPEED
            LEFT -> position.x -= delta * Constants.BULLET_SPEED
        }

        // World width from the level's viewport
        val worldWidth = level.viewport.worldWidth

        // Level's viewport's camera's horizontal position
        val camXposition = level.viewport.camera.position.x

        // If the bullet is offscreen, set active = false
        if (position.x < camXposition - worldWidth / 2
                || position.x > camXposition + worldWidth / 2) isActive = false

        //Check if the bullet is within the enemy
        for (enemy in level.enemies) {
            if (position.dst(Vector2(
                            enemy.position.x + ENEMY_COLLISION_RADIUS,
                            enemy.position.y + ENEMY_COLLISION_RADIUS
                    )) < (ENEMY_COLLISION_RADIUS)) {
                // Add the ENEMY_HIT_SCORE to the level.score
                level.score += ENEMY_HIT_SCORE

                // Spawn an explosion
                level.spawnExplosion(position)
                isActive = false
                enemy.healthCounter--
            }
        }
    }

    fun render(batch: SpriteBatch) {
        drawBatch(batch, Assets.bulletAssets.bullet, position)
    }
}