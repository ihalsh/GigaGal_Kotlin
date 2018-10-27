package com.udacity.gamedev

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport

class MyGdxGame : ApplicationAdapter() {

private val EXPLOSION_SPAWN_RATE = 20f
    private val EXPLOSION_FRAME_DURATION = 0.1f
    private val WALK_LOOP_FRAME_DURATION = 0.1f

    private lateinit var batch: SpriteBatch
    private lateinit var viewport: ExtendViewport

    private var startTime: Long = 0

    private lateinit var walkLoop: Animation<TextureRegion>
    private lateinit var explosion: Animation<TextureRegion>
    private lateinit var explosions: DelayedRemovalArray<OneShotAnimation>

    override fun create() {
        batch = SpriteBatch()
        viewport = ExtendViewport(100f, 100f)

        // Set startTime using TimeUtils.nanoTime()
        startTime = TimeUtils.nanoTime()

        val walkLoopTextures = Array<TextureRegion>()

        // Add walk-1-right.png to walkLoopTextures
        walkLoopTextures.add(TextureRegion(Texture("walk-1-right.png")))

        // Add walk-2-right.png to walkLoopTextures
        walkLoopTextures.add(TextureRegion(Texture("walk-2-right.png")))

        // Add walk-3-right.png to walkLoopTextures
        walkLoopTextures.add(TextureRegion(Texture("walk-3-right.png")))

        // Initialize walkLoop with a new animation in LOOP_PINGPONG mode
        // Use WALK_LOOP_FRAME_DURATION
        walkLoop = Animation(
                WALK_LOOP_FRAME_DURATION,
                walkLoopTextures,
                Animation.PlayMode.LOOP_PINGPONG
        )

        val explosionTextures = Array<TextureRegion>()
        explosionTextures.add(TextureRegion(Texture("explosion-large.png")))
        explosionTextures.add(TextureRegion(Texture("explosion-medium.png")))
        explosionTextures.add(TextureRegion(Texture("explosion-small.png")))
        explosion = Animation(EXPLOSION_FRAME_DURATION, explosionTextures, Animation.PlayMode.NORMAL)
        explosions = DelayedRemovalArray()

    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render() {
        updateExplosions()
        viewport.apply()
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.projectionMatrix = viewport.camera.combined
        batch.begin()

        // Compute the elapsed time in seconds since startTime
        val elapsedTime = MathUtils.nanoToSec.times(TimeUtils.nanoTime() - startTime)

        // Use getKeyFrame() to get the right frame from the walk loop
        val walkLoopTexture = walkLoop.getKeyFrame(elapsedTime)

        drawRegionCentered(
                batch,
                walkLoopTexture,
                viewport.worldWidth / 2,
                viewport.worldHeight / 2
        )

        for (explosion in explosions) {
            drawRegionCentered(
                    batch,
                    explosion.frame,
                    explosion.position.x,
                    explosion.position.y
            )
        }

        batch.end()
    }

    private fun drawRegionCentered(batch: SpriteBatch, region: TextureRegion, x: Float, y: Float) {
        batch.draw(
                region.texture,
                x - region.regionWidth / 2,
                y - region.regionHeight / 2,
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

    private fun updateExplosions() {

        // Remove explosions that are done
        explosions.begin()
        for (i in 0 until explosions.size) {
            if (explosions.get(i).isAnimationFinished) {
                explosions.removeIndex(i)
            }
        }
        explosions.end()

        // Randomly spawn a new explosion
        if (MathUtils.random() < Gdx.graphics.deltaTime * EXPLOSION_SPAWN_RATE) {
            val position = Vector2(
                    MathUtils.random(viewport.worldWidth),
                    MathUtils.random(viewport.worldWidth)
            )
            explosions.add(OneShotAnimation(explosion, position, TimeUtils.nanoTime()))
        }
    }

    inner class OneShotAnimation(
            private val animation: Animation<TextureRegion>,
            val position: Vector2,
            private val startTimeNanos: Long) {

        val frame: TextureRegion
            get() = animation.getKeyFrame(elapsedTime())

        val isAnimationFinished: Boolean
            get() = animation.isAnimationFinished(elapsedTime())

        private fun elapsedTime(): Float {
            return MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTimeNanos)
        }
    }
}
