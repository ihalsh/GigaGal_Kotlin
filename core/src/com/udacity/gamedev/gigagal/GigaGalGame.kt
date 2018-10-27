package com.udacity.gamedev.gigagal

import com.badlogic.gdx.Game

class GigaGalGame : Game() {

    override fun create() {
        setScreen(GameplayScreen())
    }

}
