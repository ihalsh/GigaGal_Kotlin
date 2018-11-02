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
    const val WORLD_SIZE = 192f

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
    const val PLATFORM_SPRITE = "platform"
    const val ENEMY_SPRITE = "enemy"

    //Constant holding the size of the stretchable edges in the platform 9 patch
    const val PLATFORM_EDGE = 8

    // Constant for GigaGal's stance width
    const val STANCE_WIDTH = 21f

    // Constant for walk loop duration
    const val WALK_LOOP_DURATION = 0.25f

    // Vector2 Constant for GigaGal's eye position within her sprites (16, 24)
    val GIGAGAL_EYE_POSITION = Vector2(16f, 24f)

    // Vector2 Constant for the center of the enemy (14, 22)
    val ENEMY_CENTER_POSITION = Vector2(14f, 22f)

    // Vector2 Constant for GigaGal's spawn position
    val GIGAGAL_SPAWN_POSITION = Vector2(20f, 120f)

    // Float constant for the height of GigaGal's eye above her feet (16)
    const val GIGAGAL_EYE_HEIGHT = 16f

    // Constant for GigaGal's movement speed
    val MOVEMENT_SPEED = Vector2(100f, 0f)

    // Constant for knockback velocity
    val KNOCKBACK_VELOCITY = Vector2(200f, 200f)

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

    // Height of the kill plane.
    const val KILL_PLANE_HEIGHT = -100f

    // Chase cam move speed constant
    const val CAMERA_MOVE_SPEED = 220

    // Constant for ENEMY_MOVEMENT_SPEED.
    const val ENEMY_MOVEMENT_SPEED = 10

    // ENEMY_BOB_AMPLITUDE constant.
    const val ENEMY_BOB_AMPLITUDE = 2f

    // ENEMY_BOB_PERIOD constant.
    const val ENEMY_BOB_PERIOD = 2.0f

    // Constant for enemy collision radius (15 is about right)
    const val ENEMY_COLLISION_RADIUS = 15f

    enum class Facing {
        LEFT, RIGHT
    }

    enum class JumpState {
        JUMPING, FALLING, GROUNDED, RECOILING
    }
    enum class WalkState {
        STANDING, WALKING
    }
}

