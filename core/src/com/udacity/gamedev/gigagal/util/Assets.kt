package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Disposable

/**
 * This utility class holds onto all the assets used in GigaGal. It's a singleton, so all
 * the entities that make up GigaGal can access their sprites in the same place, and no work loading
 * up textures is repeated.
 *
 * Each entity gets its own inner class to hold its assets. Below you'll complete the GigaGalAssets
 * class by finding up the standing-right AtlasRegion within the TextureAtlas loaded in init() .
 */

object Assets : Disposable, AssetErrorListener {

    private val TAG = Assets::class.java.simpleName
    private val assetManager by lazy { AssetManager() }
    lateinit var gigaGalAssets: GigaGalAssets

    init {
        with(assetManager) {
            setErrorListener(Assets)
            load(Constants.TEXTURE_ATLAS, TextureAtlas::class.java)
            finishLoading()
            gigaGalAssets = GigaGalAssets(get<TextureAtlas>(Constants.TEXTURE_ATLAS))
        }
    }

    override fun error(asset: AssetDescriptor<*>, throwable: Throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable)
    }

    override fun dispose() {
        assetManager.dispose()
    }

    class GigaGalAssets(atlas: TextureAtlas) {
        // Use atlas.findRegion() to initialize the standing right AtlasRegion
        val standingRight = atlas.findRegion(Constants.STANDING_RIGHT)
    }
}
