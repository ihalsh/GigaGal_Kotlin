package com.udacity.gamedev.gigagal

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.Constants.BACKGROUND_COLOR
import com.udacity.gamedev.gigagal.util.Constants.WORLD_SIZE
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.graphics.use
import ktx.log.logger

class GameplayScreen(
        private val spriteBatch: SpriteBatch = SpriteBatch(),
        private val viewport: ExtendViewport = ExtendViewport(WORLD_SIZE, WORLD_SIZE),
        private val level: Level = Level()
) : KtxScreen {

    override fun render(delta: Float) {

        // Call update() on the Level
        level.update(delta)

        // Apply the viewport
        viewport.apply()

        // Clear the screen to the BACKGROUND_COLOR
        clearScreen(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b)

        // KTX way of using SpriteBatch
        spriteBatch.use {
            // Set the SpriteBatch's projection matrix
            it.projectionMatrix = viewport.camera.combined
            level.render(it)
        }
    }

    override fun resize(width: Int, height: Int) {
        // Update the viewport
        viewport.update(width, height, true)
    }

    override fun dispose() {
        // Dispose of the Assets instance
        Assets.dispose()

        // Dispose of the SpriteBatch
        spriteBatch.dispose()
    }

    companion object {
        val logger = logger<GameplayScreen>()
    }
}
