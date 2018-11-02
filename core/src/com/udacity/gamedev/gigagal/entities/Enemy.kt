package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_CENTER_POSITION
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_MOVEMENT_SPEED
import com.udacity.gamedev.gigagal.util.Constants.Facing.LEFT
import com.udacity.gamedev.gigagal.util.Constants.Facing.RIGHT
import com.udacity.gamedev.gigagal.util.Utils.Companion.drawBatch

class Enemy(private val platform: Platform = Platform(),
            private val position: Vector2 = Vector2(platform.left - ENEMY_CENTER_POSITION.x, platform.top),
            private var direction: Constants.Facing = RIGHT) {

    fun update(delta: Float) {

        // Move the enemy left/right the appropriate amount
        when (direction) {
            RIGHT -> {
                position.x += delta * ENEMY_MOVEMENT_SPEED
                if (position.x > platform.right - ENEMY_CENTER_POSITION.x) {
                    direction = LEFT
                    position.x = platform.right - ENEMY_CENTER_POSITION.x
                }
            }
            LEFT -> {
                position.x -= delta * ENEMY_MOVEMENT_SPEED
                if (position.x < platform.left - ENEMY_CENTER_POSITION.x) {
                    direction = RIGHT
                    position.x = platform.left - ENEMY_CENTER_POSITION.x
                }
            }
        }
    }

    fun render(batch: SpriteBatch) {

        // Draw the platform using the NinePatch
        val region = Assets.enemyAssets.enemy
        drawBatch(batch, region, position)
    }
}
