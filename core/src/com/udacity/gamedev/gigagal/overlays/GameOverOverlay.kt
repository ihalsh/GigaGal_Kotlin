package com.udacity.gamedev.gigagal.overlays

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.GAME_OVER_MESSAGE
import ktx.graphics.use

class GameOverOverlay(
        val viewport: Viewport = ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE),
        private val font: BitmapFont = BitmapFont(Gdx.files.internal(Constants.FONT_FILE))) {

    fun init() {
        font.data.setScale(1f)

    }

    fun render(batch: SpriteBatch) {

        // Feel free to get more creative with this screen.
        // Perhaps you could cover the screen in enemy robots?

        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        batch.use { font.draw(
                batch,
                GAME_OVER_MESSAGE,
                viewport.worldWidth / 2,
                viewport.worldHeight / 2.5f,
                0f,
                Align.center,
                false
        ) }
    }
}
