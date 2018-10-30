package com.udacity.gamedev.gigagal

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.udacity.gamedev.gigagal.entities.GigaGal


class Level (val gigaGal:GigaGal = GigaGal()){

    fun update(delta: Float) {
        // Update GigaGal
        gigaGal.update(delta)
    }

    fun render(batch: SpriteBatch) {
        // Render GigaGal
        gigaGal.render(batch)
    }
}