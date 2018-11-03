package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.Facing.LEFT
import com.udacity.gamedev.gigagal.util.Constants.Facing.RIGHT
import com.udacity.gamedev.gigagal.util.Utils.Companion.drawBatch

class Bullet(private val position: Vector2 = Vector2(),
             private val direction: Constants.Facing = RIGHT) {

    fun update(delta: Float) {

        when (direction) {
            RIGHT -> position.x += delta * Constants.BULLET_SPEED
            LEFT -> position.x -= delta * Constants.BULLET_SPEED
        }
    }

    fun render(batch: SpriteBatch) {
        drawBatch(batch, Assets.bulletAssets.bullet, position)
    }
}