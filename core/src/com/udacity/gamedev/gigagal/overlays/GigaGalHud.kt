package com.udacity.gamedev.gigagal.overlays

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.Constants.HUD_MARGIN
import com.udacity.gamedev.gigagal.util.Constants.HUD_VIEWPORT_SIZE
import com.udacity.gamedev.gigagal.util.Utils.Companion.drawBatch
import ktx.graphics.use

class GigaGalHud(val viewport: ExtendViewport = ExtendViewport(
        HUD_VIEWPORT_SIZE,
        HUD_VIEWPORT_SIZE),
                 private var font: BitmapFont = BitmapFont()) {

    fun render(batch: SpriteBatch, lives: Int, score: Int, ammo: Int) {

        // Apply the viewport
        viewport.apply()

        // Set the projection matrix
        batch.projectionMatrix = viewport.camera.combined

        // Begin a batch
        batch.use {
            // Draw GigaGal's score and ammo count in the top left of the viewport
            font.color = Color.RED
            font.draw(it,
                    "Score: $score \nAmmo: $ammo",
                    HUD_MARGIN,
                    viewport.worldHeight - HUD_MARGIN,
                    0f,
                    Align.left,
                    false)

            // Draw a tiny GigaGal in the top right for each life left
            for (i in 1..lives) drawBatch(it, Assets.gigaGalAssets.standingRight, Vector2(
                            viewport.worldWidth - HUD_MARGIN * 2f * i,
                            viewport.worldHeight - HUD_MARGIN * 2.5f))
        }
    }
}
