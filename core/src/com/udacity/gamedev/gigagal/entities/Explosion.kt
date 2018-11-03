package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.udacity.gamedev.gigagal.util.Assets.explosionAssets
import com.udacity.gamedev.gigagal.util.Utils.Companion.drawBatch
import com.udacity.gamedev.gigagal.util.Utils.Companion.timeSinceInSec

class Explosion(private val position: Vector2,
                private val startTime: Long) {

    fun render(batch: SpriteBatch) {

        val region: TextureAtlas.AtlasRegion =
                explosionAssets.explosionAnimation.getKeyFrame(timeSinceInSec(startTime))
        drawBatch(batch, region, Vector2(position))
    }

    fun isFinished(): Boolean =
            explosionAssets.explosionAnimation.isAnimationFinished(timeSinceInSec(startTime))
}