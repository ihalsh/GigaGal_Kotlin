package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.graphics.Camera
import com.udacity.gamedev.gigagal.entities.GigaGal

class ChaseCam (private val camera: Camera,
                private val target: GigaGal){

    // Set the camera's position to GigaGal's position
    // Note that the camera's position is a Vector3, while GigaGal's position is a Vector2
    fun update() {
        camera.position.x = target.position.x
        camera.position.y = target.position.y
    }
}
