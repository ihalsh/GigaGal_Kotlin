package com.udacity.gamedev.gigagal

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.udacity.gamedev.gigagal.entities.Enemy
import com.udacity.gamedev.gigagal.entities.GigaGal
import com.udacity.gamedev.gigagal.entities.Platform
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Constants.EXPLOSION_DURATION
import com.udacity.gamedev.gigagal.util.Constants.GIGAGAL_EYE_HEIGHT
import com.udacity.gamedev.gigagal.util.Constants.GIGAGAL_SPAWN_POSITION
import com.udacity.gamedev.gigagal.util.Constants.STANCE_WIDTH
import com.udacity.gamedev.gigagal.util.Utils
import com.udacity.gamedev.gigagal.util.Utils.Companion.drawBatch
import ktx.graphics.use

class Level(private val platforms: Array<Platform> = Array(),
            val enemies: Array<Enemy> = DelayedRemovalArray()
            /*,private val enemy: Enemy = Enemy()*/) {

    val gigaGal: GigaGal = GigaGal(level = this)

    init {

        // Add a test platforms
        platforms.add(Platform(10f, 60f, 50f, 20f))
        platforms.add(Platform(20f, 120f, 60f, 20f))
        platforms.add(Platform(70f, 30f, 20f, 20f))
        val enemyPlatform = Platform(80f, 80f, 70f, 30f)
        platforms.add(enemyPlatform)
        platforms.add(Platform(150f, 60f, 50f, 20f))
        platforms.add(Platform(210f, 90f, 40f, 9f))
        platforms.add(Platform(260f, 140f, 40f, 9f))
        platforms.add(Platform(310f, 190f, 40f, 9f))
        platforms.add(Platform(360f, 140f, 40f, 9f))
        platforms.add(Platform(410f, 190f, 40f, 9f))
        platforms.add(Platform(460f, 140f, 40f, 9f))
        platforms.add(Platform(510f, 90f, 40f, 9f))
        platforms.add(Platform(560f, 60f, 50f, 20f))

        // Add an enemy sitting on enemyPlatform
        enemies.add(Enemy(enemyPlatform))
    }

    fun update(delta: Float) {
        // Update GigaGal
        gigaGal.update(delta, platforms)

        // Update the enemies
        for (enemy in enemies) enemy.update(delta)
    }

    fun render(batch: SpriteBatch) {

        // Render all platforms in the platform array
        for (platform in platforms)
            batch.use { platform.render(it) }

        // KTX way of using SpriteBatch
        batch.use { gigaGal.render(it) }

        Utils.boundsRender(gigaGal = gigaGal, batch = batch)

        // Render the enemies
        for (enemy in enemies) batch.use { enemy.render(it) }

        // Test draw the bullet
        batch.use { drawBatch(batch, Assets.bulletAssets.bullet, Vector2(40f, 68f)) }

        // Test draw the powerup
        batch.use { drawBatch(batch, Assets.powerupAssets.powerup, Vector2(50f, 68f)) }

        // Test draw frames of the explosion
        batch.use { drawBatch(
                batch,
                Assets.explosionAssets.explosionAnimation.getKeyFrame(0f),
                Vector2(0f, 68f)
        ) }

        batch.use { drawBatch(
                batch,
                Assets.explosionAssets.explosionAnimation.getKeyFrame(EXPLOSION_DURATION),
                Vector2(0f, 50f)
        ) }
    }
}