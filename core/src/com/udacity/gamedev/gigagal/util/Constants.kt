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
    const val WORLD_SIZE = 208f

    const val TEXTURE_ATLAS = "images/gigagal.pack.atlas"

    // Scoring
    val ENEMY_KILL_SCORE = 100
    val ENEMY_HIT_SCORE = 25
    val POWERUP_SCORE = 50

    // HUD
    val HUD_VIEWPORT_SIZE = 480f
    val HUD_MARGIN = 20f
    val HUD_AMMO_LABEL = "Ammo: "
    val HUD_SCORE_LABEL = "Score: "

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

    // GigaGal
    const val STANCE_WIDTH = 21f
    const val WALK_LOOP_DURATION = 0.25f
    val GIGAGAL_EYE_POSITION = Vector2(16f, 24f)
    val GIGAGAL_SPAWN_POSITION = Vector2(16f, 177f)
    const val GIGAGAL_EYE_HEIGHT = 16f
    val MOVEMENT_SPEED = Vector2(100f, 0f)
    val KNOCKBACK_VELOCITY = Vector2(200f, 200f)
    const val JUMP_SPEED = 250f
    const val MAX_JUMP_DURATION = 0.2
    const val GRAVITY = 1000
    const val INITIAL_AMMO = 10
    const val INITIAL_LIVES = 3

    // ENEMY
    const val ENEMY_MOVEMENT_SPEED = 10
    const val ENEMY_BOB_AMPLITUDE = 2f
    const val ENEMY_BOB_PERIOD = 2.0f
    const val ENEMY_COLLISION_RADIUS = 15f
    val ENEMY_CENTER_POSITION = Vector2(14f, 22f)
    const val ENEMY_HEALTH = 5

    // Bullets
    const val BULLET_SPRITE = "bullet"
    val BULLET_CENTER = Vector2(3f, 2f)
    const val BULLET_SPEED = 250f
    const val BULLET_DELAY = 0.1

    // Explosions
    const val EXPLOSION_LARGE = "explosion-large"
    const val EXPLOSION_MEDIUM = "explosion-medium"
    const val EXPLOSION_SMALL = "explosion-small"
    val EXPLOSION_CENTER = Vector2(8f, 8f)
    const val EXPLOSION_DURATION = 0.2f

    // Powerups
    const val POWERUP_SPRITE = "powerup"
    val POWERUP_CENTER = Vector2(7f, 5f)
    const val POWERUP_AMMO = 10

    // Height of the kill plane.
    const val KILL_PLANE_HEIGHT = -100f

    // Chase cam move speed constant
    const val CAMERA_MOVE_SPEED = 220

    // Level Loading
    const val LEVEL_DIR = "levels"
    const val LEVEL_FILE_EXTENSION = "dt"
    const val LEVEL_COMPOSITE = "composite"
    const val LEVEL_9PATCHES = "sImage9patchs"
    const val LEVEL_IMAGES = "sImages"
    const val LEVEL_ERROR_MESSAGE = "There was a problem loading the level."
    const val LEVEL_IMAGENAME_KEY = "imageName"
    const val LEVEL_X_KEY = "x"
    const val LEVEL_Y_KEY = "y"
    const val LEVEL_WIDTH_KEY = "width"
    const val LEVEL_HEIGHT_KEY = "height"
    const val LEVEL_IDENTIFIER_KEY = "itemIdentifier"
    const val LEVEL_ENEMY_TAG = "Enemy"

    // Exit Portal
    const val EXIT_PORTAL_SPRITE_1 = "exit-portal-1"
    const val EXIT_PORTAL_SPRITE_2 = "exit-portal-2"
    const val EXIT_PORTAL_SPRITE_3 = "exit-portal-3"
    const val EXIT_PORTAL_SPRITE_4 = "exit-portal-4"
    const val EXIT_PORTAL_SPRITE_5 = "exit-portal-5"
    const val EXIT_PORTAL_SPRITE_6 = "exit-portal-6"
    val EXIT_PORTAL_CENTER = Vector2(31f, 31f)
    val EXIT_PORTAL_RADIUS = 28f
    const val EXIT_PORTAL_FRAME_DURATION = .1f
    val EXIT_PORTAL_LOCATION = Vector2(200f, 200f)

    // Onscreen Controls
    const val ONSCREEN_CONTROLS_VIEWPORT_SIZE = 200f
    const val MOVE_LEFT_BUTTON = "button-move-left"
    const val MOVE_RIGHT_BUTTON = "button-move-right"
    const val SHOOT_BUTTON = "button-shoot"
    const val JUMP_BUTTON = "button-jump"
    val BUTTON_CENTER = Vector2(15f, 15f)
    const val BUTTON_RADIUS = 32f

    // Victory/Game Over screens
    const val LEVEL_END_DURATION = 5f
    const val VICTORY_MESSAGE = "You are the Winrar!"
    const val GAME_OVER_MESSAGE = "Game Over, Gal"
    const val EXPLOSION_COUNT = 500
    const val ENEMY_COUNT = 200
    const val FONT_FILE = "font/header.fnt"

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

