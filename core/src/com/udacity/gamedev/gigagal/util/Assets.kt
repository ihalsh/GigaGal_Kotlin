package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Disposable
import com.udacity.gamedev.gigagal.util.Constants.BULLET_SPRITE
import com.udacity.gamedev.gigagal.util.Constants.ENEMY_SPRITE
import com.udacity.gamedev.gigagal.util.Constants.EXPLOSION_DURATION
import com.udacity.gamedev.gigagal.util.Constants.EXPLOSION_LARGE
import com.udacity.gamedev.gigagal.util.Constants.EXPLOSION_MEDIUM
import com.udacity.gamedev.gigagal.util.Constants.EXPLOSION_SMALL
import com.udacity.gamedev.gigagal.util.Constants.JUMPING_LEFT
import com.udacity.gamedev.gigagal.util.Constants.JUMPING_RIGHT
import com.udacity.gamedev.gigagal.util.Constants.PLATFORM_EDGE
import com.udacity.gamedev.gigagal.util.Constants.PLATFORM_SPRITE
import com.udacity.gamedev.gigagal.util.Constants.POWERUP_SPRITE
import com.udacity.gamedev.gigagal.util.Constants.STANDING_LEFT
import com.udacity.gamedev.gigagal.util.Constants.STANDING_RIGHT
import com.udacity.gamedev.gigagal.util.Constants.WALK_1_LEFT
import com.udacity.gamedev.gigagal.util.Constants.WALK_1_RIGHT
import com.udacity.gamedev.gigagal.util.Constants.WALK_2_LEFT
import com.udacity.gamedev.gigagal.util.Constants.WALK_2_RIGHT
import com.udacity.gamedev.gigagal.util.Constants.WALK_3_LEFT
import com.udacity.gamedev.gigagal.util.Constants.WALK_3_RIGHT
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
    var gigaGalAssets: GigaGalAssets
    var platformAssets: PlatformAssets
    var enemyAssets: EnemyAssets
    var bulletAssets: BulletAssets
    var explosionAssets: ExplosionAssets
    var powerupAssets: PowerupAssets

    init {
        with(assetManager) {
            setErrorListener(Assets)
            load(Constants.TEXTURE_ATLAS, TextureAtlas::class.java)
            finishLoading()
            val atlas = get<TextureAtlas>(Constants.TEXTURE_ATLAS)
            gigaGalAssets = GigaGalAssets(atlas)
            platformAssets = PlatformAssets(atlas)
            enemyAssets = EnemyAssets(atlas)
            bulletAssets = BulletAssets(atlas)
            explosionAssets = ExplosionAssets(atlas)
            powerupAssets = PowerupAssets(atlas)
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
            val standingLeft: TextureAtlas.AtlasRegion = atlas.findRegion(STANDING_LEFT),
            val standingRight: TextureAtlas.AtlasRegion = atlas.findRegion(STANDING_RIGHT),
            val jumpingLeft: TextureAtlas.AtlasRegion = atlas.findRegion(JUMPING_LEFT),
            val jumpingRight: TextureAtlas.AtlasRegion = atlas.findRegion(JUMPING_RIGHT)) {

        val prepareLeftAnimation = { atlas: TextureAtlas ->
            val walkingLeftFrames = Array<TextureAtlas.AtlasRegion>()
            walkingLeftFrames.add(atlas.findRegion(WALK_2_LEFT))
            walkingLeftFrames.add(atlas.findRegion(WALK_1_LEFT))
            walkingLeftFrames.add(atlas.findRegion(WALK_2_LEFT))
            walkingLeftFrames.add(atlas.findRegion(WALK_3_LEFT))
            Animation(WALK_LOOP_DURATION, walkingLeftFrames, Animation.PlayMode.LOOP)
        }
        val prepareRightAnimation = { atlas: TextureAtlas ->
            val walkingRightFrames = Array<TextureAtlas.AtlasRegion>()
            walkingRightFrames.add(atlas.findRegion(WALK_2_RIGHT))
            walkingRightFrames.add(atlas.findRegion(WALK_1_RIGHT))
            walkingRightFrames.add(atlas.findRegion(WALK_2_RIGHT))
            walkingRightFrames.add(atlas.findRegion(WALK_3_RIGHT))
            Animation(WALK_LOOP_DURATION, walkingRightFrames, Animation.PlayMode.LOOP)
        }

        val walkingLeftAnimation = prepareLeftAnimation(atlas)
        val walkingRightAnimation = prepareRightAnimation(atlas)
    }

    class PlatformAssets(atlas: TextureAtlas,
                         val platformNinePatch: NinePatch
                         = NinePatch(atlas.findRegion(PLATFORM_SPRITE),
                                 PLATFORM_EDGE,
                                 PLATFORM_EDGE,
                                 PLATFORM_EDGE,
                                 PLATFORM_EDGE))

    class EnemyAssets(atlas: TextureAtlas,
                      val enemy: TextureAtlas.AtlasRegion = atlas.findRegion(ENEMY_SPRITE))

    class BulletAssets(atlas: TextureAtlas,
                      val bullet: TextureAtlas.AtlasRegion = atlas.findRegion(BULLET_SPRITE))

    class PowerupAssets(atlas: TextureAtlas,
                       val powerup: TextureAtlas.AtlasRegion = atlas.findRegion(POWERUP_SPRITE))

    class ExplosionAssets(atlas: TextureAtlas) {

        private val prepareExplosionAnimation = { atlas: TextureAtlas ->
            val explosionFrames = Array<TextureAtlas.AtlasRegion>()
            explosionFrames.add(atlas.findRegion(EXPLOSION_SMALL))
            explosionFrames.add(atlas.findRegion(EXPLOSION_MEDIUM))
            explosionFrames.add(atlas.findRegion(EXPLOSION_LARGE))
            Animation(EXPLOSION_DURATION, explosionFrames, Animation.PlayMode.NORMAL)
        }

        val explosionAnimation = prepareExplosionAnimation(atlas)
    }
}


