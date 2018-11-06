package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.udacity.gamedev.gigagal.util.Assets.explosionAssets
import com.udacity.gamedev.gigagal.util.Constants.EXPLOSION_CENTER
import com.udacity.gamedev.gigagal.util.Utils
import com.udacity.gamedev.gigagal.util.Utils.Companion.drawBatch
import com.udacity.gamedev.gigagal.util.Utils.Companion.timeSinceInSec
import ktx.log.info

class Explosion(private val position: Vector2,
                private val startTime: Long = TimeUtils.nanoTime(),
                private var offset: Float = 0f) {

    fun render(batch: SpriteBatch) {

        if (!isFinished() && !yetToStart()) {
            val region: TextureAtlas.AtlasRegion =
                    explosionAssets.explosionAnimation
                            .getKeyFrame(timeSinceInSec(startTime) - offset)
            drawBatch(
                    batch = batch,
                    region = region,
                    position = Vector2(position.x - EXPLOSION_CENTER.x,
                            position.y - EXPLOSION_CENTER.y)
            )
        }
    }

    private fun yetToStart(): Boolean = Utils.timeSinceInSec(startTime) - offset < 0
    fun isFinished(): Boolean {
        val elapsedTime = timeSinceInSec(startTime) - offset
        return explosionAssets.explosionAnimation.isAnimationFinished(elapsedTime)
    }


}

