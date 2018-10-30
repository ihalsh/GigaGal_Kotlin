package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import java.util.concurrent.TimeUnit

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

    // A constants for the names of sprites
    const val STANDING_LEFT = "standing-left"
    const val STANDING_RIGHT = "standing-right"
    const val JUMPING_LEFT = "jumping-left"
    const val JUMPING_RIGHT = "jumping-right"
    const val WALK_1_LEFT = "walk-1-left"
    const val WALK_1_RIGHT = "walk-1-right"
    const val WALK_2_LEFT = "walk-2-left"
    const val WALK_2_RIGHT = "walk-2-right"
    const val WALK_3_LEFT = "walk-3-left"
    const val WALK_3_RIGHT = "walk-3-right"

    // Constant for walk loop duration
    const val WALK_LOOP_DURATION = 0.25f

    // Vector2 Constant for GigaGal's eye position within her sprites (16, 24)
    val GIGAGAL_EYE_POSITION = Vector2(16f, 24f)

    // Float constant for the height of GigaGal's eye above her feet (16)
    const val GIGAGAL_EYE_HEIGHT = 16f

    // Constant for GigaGal's movement speed
    val MOVEMENT_SPEED = Vector2(75f, 0f)

    // Constant for GigaGal's jump speed
    // Something around 250 works well.
    const val JUMP_SPEED = 250f

    // Constant for GigaGal's max jump duration
    // Meaning how long you can hold the jump key to continue to jump higher.
    // 0.15 seconds works well
    const val MAX_JUMP_DURATION = 0.15

    // Constant for acceleration due to gravity
    // Something like 1000 works well.
    const val GRAVITY = 1000

    enum class Facing {
        LEFT, RIGHT
    }

    enum class JumpState {
        JUMPING, FALLING, GROUNDED
    }
    enum class WalkState {
        STANDING, WALKING
    }
}

