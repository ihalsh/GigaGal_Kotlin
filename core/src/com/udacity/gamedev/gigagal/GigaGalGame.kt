package com.udacity.gamedev.gigagal

import com.badlogic.gdx.Screen
import ktx.app.KtxGame

class GigaGalGame : KtxGame<Screen>() {

    override fun create() {

        // Registering GameplayScreen in the game object: it will be
        // accessible through GameplayScreen class:
        addScreen(GameplayScreen())

        // Changing current screen to the registered instance of the
        // GameplayScreen class:
        setScreen<GameplayScreen>()
    }
}
