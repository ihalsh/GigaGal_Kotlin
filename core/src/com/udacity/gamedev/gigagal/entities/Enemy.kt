package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils.*
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils.nanoTime
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_BOB_AMPLITUDE
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_BOB_PERIOD
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_CENTER_POSITION
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_MOVEMENT_SPEED
import com.udacity.gamedev.gigagal.util.Constants.Facing.LEFT
import com.udacity.gamedev.gigagal.util.Constants.Facing.RIGHT
import com.udacity.gamedev.gigagal.util.Utils.Companion.drawBatch

class Enemy(private val platform: Platform = Platform(),
            val position: Vector2 = Vector2(platform.left - ENEMY_CENTER_POSITION.x, platform.top),
            private var direction: Constants.Facing = RIGHT,
            private val startTime: Long = nanoTime()) {

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

        // Figure out where in the bob cycle we're at
        // bobMultiplier = 1 + sin(2 PI elapsedTime / period)
        val elapsedTime = nanoToSec * (nanoTime() - startTime)
        val bobMultiplier = 1 + sin(PI2 * elapsedTime / ENEMY_BOB_PERIOD)

        // Set the enemy vertical position
        position.y = platform.top + ENEMY_BOB_AMPLITUDE * bobMultiplier

        //My way
//        position.y = platform.top + Math.sin(position.x.toDouble()
//                / ENEMY_BOB_PERIOD).toFloat() * ENEMY_BOB_AMPLITUDE + ENEMY_BOB_AMPLITUDE
    }

    fun render(batch: SpriteBatch) {

        // Draw the platform using the NinePatch
        val region = Assets.enemyAssets.enemy
        drawBatch(batch, region, position)
    }
}
