package com.udacity.gamedev.gigagal.overlays

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.udacity.gamedev.gigagal.util.Constants.HUD_VIEWPORT_SIZE
import ktx.graphics.use

class GigaGalHud(val viewport: ExtendViewport = ExtendViewport(
        HUD_VIEWPORT_SIZE,
        HUD_VIEWPORT_SIZE),
                 private var font: BitmapFont = BitmapFont()) {

    fun render(batch: SpriteBatch) {

        // Apply the viewport
        viewport.apply()

        // Set the projection matrix
        batch.projectionMatrix = viewport.camera.combined

        // Begin a batch
        batch.use {
            font.draw(it,
                    "Testing, testing. Are you there, GigaGal?",
                    viewport.worldWidth / 2,
                    viewport.worldHeight / 4,
                    0f,
                    Align.center,
                    false)
        }
    }
}
