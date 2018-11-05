package com.udacity.gamedev.gigagal.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.udacity.gamedev.gigagal.util.Assets.exitPortalAssets
import com.udacity.gamedev.gigagal.util.Constants
import com.udacity.gamedev.gigagal.util.Utils.Companion.drawBatch
import com.udacity.gamedev.gigagal.util.Utils.Companion.timeSinceInSec

class ExitPortal(val position: Vector2 = Constants.EXIT_PORTAL_LOCATION,
                 private val startTime: Long = TimeUtils.nanoTime()) {

    fun render(batch: SpriteBatch) {

        // Get the time elapsed since startTime (Utils.secondsSince()),
        // get the right texture region from the exitPortalAssets,
        // draw that texture region
        val region: TextureAtlas.AtlasRegion = exitPortalAssets.portalAnimation
                        .getKeyFrame(timeSinceInSec(startTime))
        drawBatch(batch, region, Vector2(position))
    }
}