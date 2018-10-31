package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.udacity.gamedev.gigagal.util.Assets.platformAssets

class Platform(
        private val left: Float = 0f,
        private val top: Float = 0f,
        private val width: Float = 0f,
        private val height: Float = 0f,
        private val bottom: Float = top - height,
        private val right: Float = left + width) {

    fun render(batch: SpriteBatch) {

        // Draw the platform using the NinePatch
        platformAssets.platformNinePatch.draw(
                batch,
                left - 1,
                bottom - 1,
                width + 2,
                height + 2
        )
    }
}


