package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class Platform(
        private val left: Float = 0f,
        private val top: Float = 0f,
        private val width: Float = 0f,
        private val height: Float = 0f,
        private val bottom: Float = top - height,
        private val right: Float = left + width) {

    fun render(renderer: ShapeRenderer) {

        // Draw a box representing the platform
        renderer.color = Color.BLUE
        renderer.rect(left, bottom, width, height)
    }
}


