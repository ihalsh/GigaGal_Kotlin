package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Disposable
import com.udacity.gamedev.gigagal.util.Constants.WALK_LOOP_DURATION

/**
 * This utility class holds onto all the assets used in GigaGal. It's a singleton, so all
 * the entities that make up GigaGal can access their sprites in the same place, and no work loading
 * up textures is repeated.
 *
 * Each entity gets its own inner class to hold its assets. Below you'll complete the GigaGalAssets
 * class by finding up the standing-right AtlasRegion within the TextureAtlas loaded in init() .
 */

object Assets : Disposable, AssetErrorListener {

    private val logger = ktx.log.logger<Assets>()
    private val assetManager by lazy { AssetManager() }
    lateinit var gigaGalAssets: GigaGalAssets

    init {
        with(assetManager) {
            setErrorListener(Assets)
            load(Constants.TEXTURE_ATLAS, TextureAtlas::class.java)
            finishLoading()
            gigaGalAssets = GigaGalAssets(get<TextureAtlas>(Constants.TEXTURE_ATLAS))
        }
        logger { "Assets loading... Ok" }
    }

    override fun error(asset: AssetDescriptor<*>, throwable: Throwable) {
//        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable)
        logger.error(throwable) { "Couldn't load asset: ${asset.fileName}" }
    }

    override fun dispose() {
        assetManager.dispose()
    }

    class GigaGalAssets(
            atlas: TextureAtlas,
            // Use atlas.findRegion() to initialize AtlasRegions
            val standingLeft: TextureAtlas.AtlasRegion = atlas.findRegion(Constants.STANDING_LEFT),
            val standingRight: TextureAtlas.AtlasRegion = atlas.findRegion(Constants.STANDING_RIGHT),
            val jumpingLeft: TextureAtlas.AtlasRegion = atlas.findRegion(Constants.JUMPING_LEFT),
            val jumpingRight: TextureAtlas.AtlasRegion = atlas.findRegion(Constants.JUMPING_RIGHT)) {

        val prepareLeftAnimation = { atlas: TextureAtlas ->
            val walkingLeftFrames = Array<TextureAtlas.AtlasRegion>()
            walkingLeftFrames.add(atlas.findRegion(Constants.WALK_2_LEFT))
            walkingLeftFrames.add(atlas.findRegion(Constants.WALK_1_LEFT))
            walkingLeftFrames.add(atlas.findRegion(Constants.WALK_2_LEFT))
            walkingLeftFrames.add(atlas.findRegion(Constants.WALK_3_LEFT))
            Animation(WALK_LOOP_DURATION, walkingLeftFrames, Animation.PlayMode.LOOP)
        }
        val prepareRightAnimation = { atlas: TextureAtlas ->
            val walkingRightFrames = Array<TextureAtlas.AtlasRegion>()
            walkingRightFrames.add(atlas.findRegion(Constants.WALK_2_RIGHT))
            walkingRightFrames.add(atlas.findRegion(Constants.WALK_1_RIGHT))
            walkingRightFrames.add(atlas.findRegion(Constants.WALK_2_RIGHT))
            walkingRightFrames.add(atlas.findRegion(Constants.WALK_3_RIGHT))
            Animation(WALK_LOOP_DURATION, walkingRightFrames, Animation.PlayMode.LOOP)
        }

        val walkingLeftAnimation = prepareLeftAnimation(atlas)
        val walkingRightAnimation = prepareRightAnimation(atlas)
    }
}


