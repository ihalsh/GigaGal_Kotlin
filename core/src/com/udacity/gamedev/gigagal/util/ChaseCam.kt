package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.graphics.Camera
import com.udacity.gamedev.gigagal.entities.GigaGal
import com.udacity.gamedev.gigagal.util.Constants.CAMERA_MOVE_SPEED

class ChaseCam(private val camera: Camera,
               private val target: GigaGal,
               private var following: Boolean = true) {

    fun update(delta: Float) {

        // Toggle the following flag when spacebar is pressed
        if (input.isKeyJustPressed(Q)) following = following.not()

        if (following) {
            camera.position.x = target.position.x
            camera.position.y = target.position.y
        } else when { // If not following
            input.isKeyPressed(A) -> camera.position.x -= delta * CAMERA_MOVE_SPEED
            input.isKeyPressed(D) -> camera.position.x += delta * CAMERA_MOVE_SPEED
            input.isKeyPressed(W) -> camera.position.y += delta * CAMERA_MOVE_SPEED
            input.isKeyPressed(S) -> camera.position.y -= delta * CAMERA_MOVE_SPEED
        }
    }
}
