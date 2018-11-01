package com.udacity.gamedev.gigagal

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.ChaseCam
import com.udacity.gamedev.gigagal.util.Constants.BACKGROUND_COLOR
import com.udacity.gamedev.gigagal.util.Constants.WORLD_SIZE
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.log.logger

class GameplayScreen(
        private val spriteBatch: SpriteBatch = SpriteBatch(),
        private val viewport: ExtendViewport = ExtendViewport(WORLD_SIZE, WORLD_SIZE),
        private val level: Level = Level(),
        private val chaseCam: ChaseCam = ChaseCam(viewport.camera, level.gigaGal)) : KtxScreen {

    override fun render(delta: Float) {

        level.update(delta)

        chaseCam.update()

        viewport.apply()

        clearScreen(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b)

        // Set the SpriteBatch's projection matrix
        spriteBatch.projectionMatrix = viewport.camera.combined

        level.render(spriteBatch)
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
