package com.udacity.gamedev.gigagal.overlays

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.udacity.gamedev.gigagal.entities.GigaGal
import com.udacity.gamedev.gigagal.util.Assets.onscreenControlsAssets
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.BUTTON_CENTER
import com.udacity.gamedev.gigagal.util.Constants.BUTTON_RADIUS
import com.udacity.gamedev.gigagal.util.Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE
import com.udacity.gamedev.gigagal.util.Utils
import ktx.graphics.use

class OnscreenControls(
        var gigaGal: GigaGal,
        val viewport: ExtendViewport = ExtendViewport(ONSCREEN_CONTROLS_VIEWPORT_SIZE,
                ONSCREEN_CONTROLS_VIEWPORT_SIZE),
        private val moveLeftCenter: Vector2 = Vector2(),
        private val moveRightCenter: Vector2 = Vector2(),
        private val shootCenter: Vector2 = Vector2(),
        private val jumpCenter: Vector2 = Vector2(),
        private var moveLeftPointer: Int = 0,
        private var moveRightPointer: Int = 0,
        private var jumpPointer: Int = 0) : InputAdapter() {


    fun init() = recalculateButtonPositions(viewport)

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        val viewportPosition = viewport.unproject(Vector2(
                screenX.toFloat(),
                screenY.toFloat()))

        when {
            viewportPosition.dst(shootCenter) < BUTTON_RADIUS ->
                            gigaGal.shoot()// Call shoot() on GigaGal
            viewportPosition.dst(jumpCenter) < BUTTON_RADIUS -> {
                // Save the jumpPointer and set gigaGal.jumpButtonPressed = true
                jumpPointer = pointer
                gigaGal.jumpButtonPressed = true
            }
            viewportPosition.dst(moveLeftCenter) < BUTTON_RADIUS -> {
                // Save the moveLeftPointer, and set gigaGal.leftButtonPressed = true
                moveLeftPointer = pointer
                gigaGal.leftButtonPressed = true
            }
            viewportPosition.dst(moveRightCenter) < BUTTON_RADIUS -> {
                // Save the moveRightPointer, and set gigaGal.rightButtonPressed = true
                moveRightPointer = pointer
                gigaGal.rightButtonPressed = true
            }
        }

        return super.touchDown(screenX, screenY, pointer, button)
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {

        val viewportPosition = viewport.unproject(Vector2(
                screenX.toFloat(),
                screenY.toFloat()))

        // Handle the case where the right button touch has been dragged to the left button
        when {pointer == moveLeftPointer && viewportPosition.dst(moveRightCenter) < BUTTON_RADIUS -> {

                // Inform GigaGal that the left button is no longer pressed
                gigaGal.leftButtonPressed = false

                // Inform GigaGal that the right button is now pressed
                gigaGal.rightButtonPressed = true

                // Zero moveLeftPointer
                moveLeftPointer = 0

                // Save moveRightPointer
                moveRightPointer = pointer

            }
        }
        // Handle the case where the right button touch has been dragged to the left button
        when {pointer == moveRightPointer && viewportPosition.dst(moveLeftCenter) < BUTTON_RADIUS -> {

                gigaGal.rightButtonPressed = false
                gigaGal.leftButtonPressed = true
                moveRightPointer = 0
                moveLeftPointer = pointer

            }
        }
        return super.touchDragged(screenX, screenY, pointer)
    }

    fun render(batch: SpriteBatch) {

        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined

        batch.use {
            if (!Gdx.input.isTouched(jumpPointer)) {
                gigaGal.jumpButtonPressed = false
                jumpPointer = 0
            }

            // If the moveLeftPointer is no longer touched, inform GigaGal and zero moveLeftPointer
            if (!Gdx.input.isTouched(moveLeftPointer)) {
                gigaGal.leftButtonPressed = false
                moveLeftPointer = 0
            }

            // Do the same for moveRightPointer
            if (!Gdx.input.isTouched(moveRightPointer)) {
                gigaGal.rightButtonPressed = false
                moveRightPointer = 0
            }

            Utils.drawBatch(
                    batch,
                    onscreenControlsAssets.moveLeft,
                    moveLeftCenter,
                    Constants.BUTTON_CENTER
            )

            Utils.drawBatch(
                    batch,
                    onscreenControlsAssets.moveRight,
                    moveRightCenter,
                    BUTTON_CENTER
            )
            Utils.drawBatch(
                    batch,
                    onscreenControlsAssets.jump,
                    jumpCenter,
                    BUTTON_CENTER
            )
            Utils.drawBatch(
                    batch,
                    onscreenControlsAssets.shoot,
                    shootCenter,
                    BUTTON_CENTER
            )
        }
    }

    fun recalculateButtonPositions(viewport: ExtendViewport) {
        moveLeftCenter.set(BUTTON_RADIUS * 3 / 4, BUTTON_RADIUS)
        moveRightCenter.set(BUTTON_RADIUS * 2, BUTTON_RADIUS * 3 / 4)

        jumpCenter.set(viewport.worldWidth - BUTTON_RADIUS * 3 / 4, BUTTON_RADIUS)
        shootCenter.set(viewport.worldWidth - BUTTON_RADIUS * 2, BUTTON_RADIUS * 3 / 4)
    }
}
