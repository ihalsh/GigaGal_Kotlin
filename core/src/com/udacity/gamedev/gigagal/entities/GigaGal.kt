package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.GIGAGAL_EYE_HEIGHT
import com.udacity.gamedev.gigagal.util.Constants.GIGAGAL_EYE_POSITION

class GigaGal(private val position: Vector2 = Vector2(20f, GIGAGAL_EYE_HEIGHT),
              private val velocity: Vector2 = Vector2()) {

    fun update(delta: Float) {

        position.mulAdd(velocity, delta)

    }

    fun render(batch: SpriteBatch) {

        // Draw GigaGal's standing-right sprite at position - GIGAGAL_EYE_POSITION

        val region = Assets.gigaGalAssets.standingRight

        batch.draw(
                region.texture,
                position.x - GIGAGAL_EYE_POSITION.x,
                position.y - GIGAGAL_EYE_POSITION.y,
                0f,
                0f,
                region.regionWidth.toFloat(),
                region.regionHeight.toFloat(),
                1f,
                1f,
                0f,
                region.regionX,
                region.regionY,
                region.regionWidth,
                region.regionHeight,
                false,
                false)

    }

    companion object {
        val TAG = GigaGal::class.java.name
    }
}
