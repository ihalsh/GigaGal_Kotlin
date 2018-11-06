package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.udacity.gamedev.gigagal.entities.GigaGal
import ktx.graphics.rect

class Utils {
    companion object {
        fun drawBatch(
                batch: SpriteBatch,
                region: TextureAtlas.AtlasRegion,
                position: Vector2,
                offset: Vector2 = Vector2()
        ) {
            batch.draw(
                    region.texture,
                    position.x - offset.x,
                    position.y - offset.y,
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

        fun timeSinceInSec(time: Long) = (MathUtils.nanoToSec * (TimeUtils.timeSinceNanos(time)))
    }
}
