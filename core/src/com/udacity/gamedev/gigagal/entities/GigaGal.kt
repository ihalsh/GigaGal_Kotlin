package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.reflect.ArrayReflection.set
import com.udacity.gamedev.gigagal.util.Assets.gigaGalAssets
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.Facing.LEFT
import com.udacity.gamedev.gigagal.util.Constants.Facing.RIGHT
import com.udacity.gamedev.gigagal.util.Constants.GIGAGAL_EYE_HEIGHT
import com.udacity.gamedev.gigagal.util.Constants.GIGAGAL_EYE_POSITION
import com.udacity.gamedev.gigagal.util.Constants.GIGAGAL_SPAWN_POSITION
import com.udacity.gamedev.gigagal.util.Constants.JUMP_SPEED
import com.udacity.gamedev.gigagal.util.Constants.JumpState.*
import com.udacity.gamedev.gigagal.util.Constants.KILL_PLANE_HEIGHT
import com.udacity.gamedev.gigagal.util.Constants.MAX_JUMP_DURATION
import com.udacity.gamedev.gigagal.util.Constants.MOVEMENT_SPEED
import com.udacity.gamedev.gigagal.util.Constants.STANCE_WIDTH
import com.udacity.gamedev.gigagal.util.Constants.WalkState.STANDING
import com.udacity.gamedev.gigagal.util.Constants.WalkState.WALKING
import ktx.log.info

class GigaGal(val position: Vector2 = Vector2(GIGAGAL_SPAWN_POSITION),
              private val lastFramePosition: Vector2 = Vector2(position),
              private val velocity: Vector2 = Vector2(),
              private var facing: Constants.Facing = RIGHT,
              private var jumpState: Constants.JumpState = FALLING,
              private var jumpStartTime: Long = 0,
              private var walkStartTime: Long = 0,
              private var walkState: Constants.WalkState = STANDING) {

    fun update(delta: Float, platforms: Array<Platform>) {
        // Update lastFramePosition
        lastFramePosition.set(position)

        // Accelerate GigaGal down
        velocity.y -= delta * Constants.GRAVITY

        // Apply GigaGal's velocity to her position
        position.mulAdd(velocity, delta)

        // If GigaGal isn't JUMPING, make her now FALLING
        if (jumpState != JUMPING) {

            jumpState = FALLING

            // Check if GigaGal has fall of the platform
            if (position.y - GIGAGAL_EYE_HEIGHT < KILL_PLANE_HEIGHT) {
                jumpState = GROUNDED
                position.set(Vector2(GIGAGAL_SPAWN_POSITION))
                velocity.y = 0f
            }

            // For each platform, call landedOnPlatform()
            for (platform in platforms) if (landedOnPlatform(platform)) {
                jumpState = GROUNDED
                position.y = platform.top + GIGAGAL_EYE_HEIGHT
                velocity.y = 0f
            }
        }
        // If the jump key wasn't pressed, endJump()
        if (input.isKeyPressed(Keys.SPACE)) {
            // Handle jump key
            when (jumpState) {
                GROUNDED -> startJump()
                JUMPING -> continueJump()
                FALLING -> endJump()
            }

        } else endJump()

        // Uses Gdx.input.isKeyPressed() to check if the left arrow key is pressed
        if (input.isKeyPressed(Keys.LEFT)) {
            moveLeft(delta)
        } else if (input.isKeyPressed(Keys.RIGHT)) {
            moveRight(delta)
        } else walkState = STANDING
    }

    private fun landedOnPlatform(platform: Platform): Boolean {

        // First check if GigaGal's feet were above the platform top last frame and below
        // the platform top this frame

        if (lastFramePosition.y - Constants.GIGAGAL_EYE_HEIGHT >= platform.top &&
                position.y - Constants.GIGAGAL_EYE_HEIGHT < platform.top) {
            // If so, find the position of GigaGal's left and right toes
            val leftFoot = position.x - STANCE_WIDTH / 2
            val rightFoot = position.x + STANCE_WIDTH / 2

            // See if either of GigaGal's toes are on the platform
            val leftFootIn = (platform.left < leftFoot && platform.right > leftFoot)

            val rightFootIn = (platform.left < rightFoot && platform.right > rightFoot)

            // See if GigaGal is straddling the platform
            val straddle = (platform.left > leftFoot && platform.right < rightFoot)

            // Return whether or not GigaGal had landed on the platform
            return leftFootIn || rightFootIn || straddle
        }
        return false
    }

    private fun moveLeft(delta: Float) {
        // If we're GROUNDED and not WALKING, save the walkStartTime
        if (jumpState == GROUNDED && walkState != WALKING) walkStartTime = TimeUtils.nanoTime()

        //Set walkState to WALKING
        walkState = WALKING

        // Update facing direction
        facing = LEFT

        // Move GigaGal left by delta * movement speed
        position.mulAdd(MOVEMENT_SPEED, -delta)
    }

    private fun moveRight(delta: Float) {
        // If we're GROUNDED and not WALKING, save the walkStartTime
        if (jumpState == GROUNDED && walkState != WALKING) walkStartTime = TimeUtils.nanoTime()

        //Set walkState to WALKING
        walkState = WALKING

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

        // Select the correct sprite based on facing, jumpState, and walkState
        val region: TextureAtlas.AtlasRegion = if (jumpState == GROUNDED) {
            if (walkState == WALKING) {
                when (facing) {
                    LEFT -> {
                        // Calculate how long we've been walking in seconds
                        val walkTimeSeconds = MathUtils.nanoToSec *
                                (TimeUtils.nanoTime() - walkStartTime)
                        // Select the correct frame from the walking right animation
                        gigaGalAssets.walkingLeftAnimation.getKeyFrame(walkTimeSeconds)
                    }
                    RIGHT -> {
                        // Calculate how long we've been walking in seconds
                        val walkTimeSeconds = MathUtils.nanoToSec *
                                (TimeUtils.nanoTime() - walkStartTime)
                        // Select the correct frame from the walking right animation
                        gigaGalAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds)
                    }
                }
            } else when (facing) /*walkState == STANDING*/ {
                LEFT -> gigaGalAssets.standingLeft
                RIGHT -> gigaGalAssets.standingRight
            }
        } else when (facing) /*jumpState != GROUNDED*/ {
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
