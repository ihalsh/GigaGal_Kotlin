package com.udacity.gamedev

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ExtendViewport

class MyGdxGame : ApplicationAdapter() {

    companion object {
        private val UDACITY_ORANGE = Color(
                228.0f / 225.0f,
                127.0f / 225.0f,
                57.0f / 225.0f,
                1.0f
        )
        const val WORLD_SIZE = 100.0f
        const val LOGO_SIZE = 0.5f * WORLD_SIZE
    }

    private lateinit var viewport: ExtendViewport

    // Declare a SpriteBatch
    private lateinit var spriteBatch: SpriteBatch

    // Declare a Texture for the Udacity Logo
    private lateinit var img: Texture

    override fun create() {
        viewport = ExtendViewport(WORLD_SIZE, WORLD_SIZE)

        // Initialize the SpriteBatch
        spriteBatch = SpriteBatch()

        // Load the Udacity logo Texture (look in android/assets)
        img = Texture("udacity_logo_white.png")


    }

    override fun render() {
        viewport.apply()
        Gdx.gl.glClearColor(
                UDACITY_ORANGE.r,
                UDACITY_ORANGE.g,
                UDACITY_ORANGE.b,
                1f
        )
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Set the SpriteBatch's projection matrix
        spriteBatch.projectionMatrix = viewport.camera.combined

        // Begin the sprite spriteBatch
        spriteBatch.begin()

        // Centering the logo
        spriteBatch.draw(
                img,
                viewport.worldWidth / 2 - LOGO_SIZE / 2,
                viewport.worldHeight / 2 - LOGO_SIZE / 2,
                LOGO_SIZE,
                LOGO_SIZE
        )

        // End the sprite spriteBatch
        spriteBatch.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }
}
