package com.udacity.gamedev.gigagal.overlays

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.udacity.gamedev.gigagal.entities.Explosion
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.EXPLOSION_COUNT
import com.udacity.gamedev.gigagal.util.Constants.FONT_FILE
import com.udacity.gamedev.gigagal.util.Constants.WORLD_SIZE
import ktx.graphics.use
import kotlin.random.Random

class VictoryOverlay(
        val viewport: ExtendViewport = ExtendViewport(WORLD_SIZE, WORLD_SIZE),
        private val font: BitmapFont = BitmapFont(Gdx.files.internal(FONT_FILE)),
        private var explosions: Array<Explosion> = Array(EXPLOSION_COUNT)) {

    fun init() {
        font.data.setScale(1f)
        // Fill the explosions array with explosions at random locations within the viewport
        // Also, set the offset of each explosion to a random float from
        // 0 -- Constants.LEVEL_END_DURATION

        for (i in 0 until EXPLOSION_COUNT) {

            val x = Random.nextFloat() * viewport.worldWidth
            val y = Random.nextFloat() * viewport.worldHeight

            explosions.add(Explosion(Vector2(x, y),
                    offset = MathUtils.random(Constants.LEVEL_END_DURATION)
            ))
        }
    }

    fun render(batch: SpriteBatch) {

        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined

        // Render the explosions/fireworks
        batch.use {
            for (explosion in explosions) explosion.render(it)
            font.draw(
                    batch,
                    Constants.VICTORY_MESSAGE,
                    viewport.worldWidth / 2,
                    viewport.worldHeight / 2.5f,
                    0f,
                    Align.center,
                    false
            )
        }
    }
}
