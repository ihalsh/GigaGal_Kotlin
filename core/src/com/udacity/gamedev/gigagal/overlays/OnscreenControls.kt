package com.udacity.gamedev.gigagal.overlays

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
        private val jumpCenter: Vector2 = Vector2()) {

    fun init() = recalculateButtonPositions(viewport)

    fun render(batch: SpriteBatch) {

        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined

        batch.use {
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

    private fun recalculateButtonPositions(viewport: ExtendViewport) {
        moveLeftCenter.set(BUTTON_RADIUS * 3 / 4, BUTTON_RADIUS)
        moveRightCenter.set(BUTTON_RADIUS * 2, BUTTON_RADIUS * 3 / 4)

        jumpCenter.set(viewport.worldWidth - BUTTON_RADIUS * 3 / 4, BUTTON_RADIUS)
        shootCenter.set(viewport.worldWidth - BUTTON_RADIUS * 2, BUTTON_RADIUS * 3 / 4)
    }
}
