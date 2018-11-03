package com.udacity.gamedev.gigagal

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.udacity.gamedev.gigagal.entities.Bullet
import com.udacity.gamedev.gigagal.entities.Enemy
import com.udacity.gamedev.gigagal.entities.GigaGal
import com.udacity.gamedev.gigagal.entities.Platform
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Utils
import ktx.graphics.use
import kotlin.random.Random

class Level(private val platforms: Array<Platform> = Array(),
            val enemies: Array<Enemy> = DelayedRemovalArray(),
            private val bullets: Array<Bullet> = DelayedRemovalArray()) {

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

    private fun spawnBullet(position: Vector2, direction: Constants.Facing) {
        bullets.add(Bullet(position, direction))
    }

    fun update(delta: Float) {
        // Update GigaGal
        gigaGal.update(delta, platforms)

        // Update the enemies
        for (enemy in enemies) enemy.update(delta)

        // BULLET STORM!

        // Spawn a bullet in a random direction, at a random position
        var bulletDirection: Constants.Facing = Constants.Facing.LEFT
        if (Math.round(Random.nextFloat()).toString() == "1")
            bulletDirection = Constants.Facing.RIGHT

        val bulletPosition = Vector2(
                10f + Random.nextFloat()*Constants.WORLD_SIZE - 10f,
                10f + Random.nextFloat()*150f
        )

        spawnBullet(position = bulletPosition, direction = bulletDirection)

        // Update the bullets
        for (bullet in bullets) bullet.update(delta)

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

        // Render the bullets
        for (bullet in bullets) batch.use { bullet.render(it) }

    }
}