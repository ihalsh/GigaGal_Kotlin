package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

object Constants {

    // BACKGROUND_COLOR We recommend Color.SKY
    val BACKGROUND_COLOR: Color = Color.SKY

    // A WORLD_SIZE
    /**
     * We'll draw our sprites at their natural size, so this is the number of pixels of our Pixel
     * art that will fit on the screen. We're going to use this size to initialize both dimensions
     * of an ExtendViewport, and we'll run the game in landscape mode, so this will really end up
     * specifying the height of the world. We recommend 128.
     */
    const val WORLD_SIZE = 128f

    const val TEXTURE_ATLAS = "images/gigagal.pack.atlas"

    // A constant for the name of the standing-right sprite
    const val STANDING_RIGHT = "standing-right"

    // Vector2 Constant for GigaGal's eye position within her sprites (16, 24)

    val GIGAGAL_EYE_POSITION = Vector2(16f, 24f)

    // Float constant for the height of GigaGal's eye above her feet (16)
    const val GIGAGAL_EYE_HEIGHT = 16f

    // Constant for GigaGal's movement speed
    val MOVEMENT_SPEED = Vector2(30f, 0f)
}
