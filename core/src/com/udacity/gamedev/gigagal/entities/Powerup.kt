package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.Utils

class Powerup(val position: Vector2) {

    fun render(batch: SpriteBatch) {
        Utils.drawBatch(batch, Assets.powerupAssets.powerup, position)
    }
}
