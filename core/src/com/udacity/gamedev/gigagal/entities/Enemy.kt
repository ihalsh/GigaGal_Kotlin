package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.udacity.gamedev.gigagal.util.Assets

class Enemy(private val platform: Platform = Platform(),
            private val position: Vector2 = Vector2(platform.left, platform.top)) {

    fun update(delta: Float) {

    }

    fun render(batch: SpriteBatch) {

        // Draw the platform using the NinePatch
        val region = Assets.enemyAssets.enemy
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
