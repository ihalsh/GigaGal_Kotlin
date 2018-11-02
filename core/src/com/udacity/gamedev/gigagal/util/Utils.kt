package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.udacity.gamedev.gigagal.entities.GigaGal
import ktx.graphics.rect

class Utils {
    companion object {
        fun drawBatch(batch: SpriteBatch, region: TextureAtlas.AtlasRegion, position: Vector2) {
            batch.draw(
                    region.texture,
                    position.x,
                    position.y,
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

        fun boundsRender(renderer: ShapeRenderer = ShapeRenderer(), gigaGal: GigaGal, batch: SpriteBatch) {
            renderer.setAutoShapeType(true)
            renderer.projectionMatrix = batch.projectionMatrix
            renderer.begin()
            renderer.rect(gigaGal.gigaGalBounds.x,
                    gigaGal.gigaGalBounds.y,
                    gigaGal.gigaGalBounds.width,
                    gigaGal.gigaGalBounds.height)

            renderer.rect(
                    gigaGal.enemyBounds.x,
                    gigaGal.enemyBounds.y,
                    gigaGal.enemyBounds.width,
                    gigaGal.enemyBounds.height)

            renderer.end()
        }
//        enemyBounds = Rectangle(
//        enemy.position.x + ENEMY_COLLISION_RADIUS,
//        enemy.position.y + 1.5f * ENEMY_COLLISION_RADIUS,
//        2 * ENEMY_COLLISION_RADIUS,
//        2 * ENEMY_COLLISION_RADIUS
    }
}
