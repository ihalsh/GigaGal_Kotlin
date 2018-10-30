package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.udacity.gamedev.gigagal.util.Assets.gigaGalAssets
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.Facing.LEFT
import com.udacity.gamedev.gigagal.util.Constants.Facing.RIGHT
import com.udacity.gamedev.gigagal.util.Constants.GIGAGAL_EYE_HEIGHT
import com.udacity.gamedev.gigagal.util.Constants.GIGAGAL_EYE_POSITION
import com.udacity.gamedev.gigagal.util.Constants.JUMP_SPEED
import com.udacity.gamedev.gigagal.util.Constants.JumpState.*
import com.udacity.gamedev.gigagal.util.Constants.MAX_JUMP_DURATION
import com.udacity.gamedev.gigagal.util.Constants.MOVEMENT_SPEED

class GigaGal(private val position: Vector2 = Vector2(20f, GIGAGAL_EYE_HEIGHT),
              private val velocity: Vector2 = Vector2(),
              private var facing: Constants.Facing = RIGHT,
              private var jumpState: Constants.JumpState = FALLING,
              private var jumpStartTime: Long = 0) {

    fun update(delta: Float) {

        // Accelerate GigaGal down
        velocity.y -= delta * Constants.GRAVITY

        // Apply GigaGal's velocity to her position
        position.mulAdd(velocity, delta)

        // If GigaGal isn't JUMPING, make her now FALLING
        if (jumpState != JUMPING) {

            jumpState = FALLING

            // Check if GigaGal has landed on the ground
            // Remember that position keeps track of GigaGal's eye position, not her feet.
            // If she has indeed landed, change her jumpState to GROUNDED, set her vertical
            // velocity to 0, and make sure her feet aren't sticking into the floor.
            if (position.y - GIGAGAL_EYE_HEIGHT < 0) {
                jumpState = GROUNDED
                position.y = GIGAGAL_EYE_HEIGHT
                velocity.y = 0f
            }
        }

        if (input.isKeyPressed(Keys.Z)) {
            logger { "Z pressed" }
            // Handle jump key
            when (jumpState) {
                GROUNDED -> startJump()
                JUMPING -> continueJump()
                FALLING -> endJump()
            }

        } else {
            // If the jump key wasn't pressed, endJump()
            endJump()

        }

        // Uses Gdx.input.isKeyPressed() to check if the left arrow key is pressed
        if (input.isKeyPressed(Keys.LEFT)) {
            logger { "LEFT pressed" }
            moveLeft(delta)
        }
        if (input.isKeyPressed(Keys.RIGHT)) {
            logger { "RIGHT pressed" }
            moveRight(delta)
        }
    }

    private fun moveLeft(delta: Float) {
        // Update facing direction
        facing = LEFT

        // Move GigaGal left by delta * movement speed
        position.mulAdd(MOVEMENT_SPEED, -delta)
    }

    private fun moveRight(delta: Float) {
        // Update facing direction
        facing = RIGHT

        // Same for moving GigaGal right
        position.mulAdd(MOVEMENT_SPEED, delta)
    }

    private fun startJump() {
        // Set jumpState to JUMPING
        jumpState = JUMPING

        // Set the jump start time
        jumpStartTime = TimeUtils.nanoTime()

        // Call continueJump()
        continueJump()

    }

    private fun continueJump() {
        // First, check if we're JUMPING, if not, just return
        if (jumpState != JUMPING) return

        // Find out how long we've been jumping
        val elapsedTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime)

        if (elapsedTime < MAX_JUMP_DURATION) velocity.y = JUMP_SPEED
        else endJump()

    }

    private fun endJump() {
        // If we're JUMPING, now we're FALLING
        if (jumpState == JUMPING) jumpState = FALLING
    }

    fun render(batch: SpriteBatch) {

        // Select the correct region based on facing and jumpState
        val region = if (jumpState == GROUNDED) when (facing) {
            LEFT -> gigaGalAssets.standingLeft
            RIGHT -> gigaGalAssets.standingRight
        } else when (facing) {
            LEFT -> gigaGalAssets.jumpingLeft
            RIGHT -> gigaGalAssets.jumpingRight
        }

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
