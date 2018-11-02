package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2

class Utils {
    companion object {
        fun drawBatch(batch: SpriteBatch, region: TextureAtlas.AtlasRegion, position: Vector2) {
            batch.draw(
                    region.texture,
                    position.x,
                    position.y,
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
    }
}
