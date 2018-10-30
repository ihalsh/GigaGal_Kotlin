package com.udacity.gamedev.gigagal

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.udacity.gamedev.gigagal.entities.GigaGal
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.Constants.BACKGROUND_COLOR
import com.udacity.gamedev.gigagal.util.Constants.WORLD_SIZE

class GameplayScreen (
        private val spriteBatch: SpriteBatch = SpriteBatch(),
        private val viewport: ExtendViewport = ExtendViewport(WORLD_SIZE, WORLD_SIZE),
        private val level:Level = Level()
        ) : ScreenAdapter() {

    override fun show() {
        // Initialize the Assets instance (not necessary)

    }

    override fun render(delta: Float) {
        // Call update() on the Level
        level.update(delta)

        // Apply the viewport
        viewport.apply()

        // Clear the screen to the BACKGROUND_COLOR
        Gdx.gl.glClearColor(
                BACKGROUND_COLOR.r,
                BACKGROUND_COLOR.g,
                BACKGROUND_COLOR.b,
                BACKGROUND_COLOR.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Set the SpriteBatch's projection matrix
        spriteBatch.projectionMatrix = viewport.camera.combined

        // Begin the SpriteBatch
        spriteBatch.begin()

        level.render(spriteBatch)

        // End the SpriteBatch
        spriteBatch.end()
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
        val TAG = GameplayScreen::class.java.name
    }
}
