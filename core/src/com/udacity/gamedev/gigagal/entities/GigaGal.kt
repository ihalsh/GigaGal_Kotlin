package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input.Keys.RIGHT
import com.badlogic.gdx.Input.Keys.LEFT
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.Constants.GIGAGAL_EYE_HEIGHT
import com.udacity.gamedev.gigagal.util.Constants.GIGAGAL_EYE_POSITION
import com.udacity.gamedev.gigagal.util.Constants.MOVEMENT_SPEED

class GigaGal(private val position: Vector2 = Vector2(20f, GIGAGAL_EYE_HEIGHT),
              private val velocity: Vector2 = Vector2(MOVEMENT_SPEED)) {

    fun update(delta: Float) {

        // Uses Gdx.input.isKeyPressed() to check if the left arrow key is pressed
        if (input.isKeyPressed(LEFT)) {
            logger{"LEFT pressed"}
            moveLeft(delta)
        }
        if (input.isKeyPressed(RIGHT)) {
            logger{"RIGHT pressed"}
            moveRight(delta)
        }
    }

    private fun moveLeft(delta: Float) {
        // Move GigaGal left by delta * movement speed
        position.mulAdd(velocity, -delta)
    }

    private fun moveRight(delta: Float) {
        // Same for moving GigaGal right
        position.mulAdd(velocity, delta)
    }

    fun render(batch: SpriteBatch) {

        // Draw GigaGal's standing-right sprite at position - GIGAGAL_EYE_POSITION

        val region = Assets.gigaGalAssets.standingRight

        batch.draw(
                region.texture,
                position.x - GIGAGAL_EYE_POSITION.x,
                position.y - GIGAGAL_EYE_POSITION.y,
                0f,
                0f,
                region.regionWidth.toFloat(),
                region.regionHeight.toFloat(),
                1f,
                1f,
                0f,
                region.regionX,
                region.regionY,
                region.regionWidth,
                region.regionHeight,
                false,
                false)
    }

    companion object {
        val logger = ktx.log.logger<GigaGal>()
    }
}
