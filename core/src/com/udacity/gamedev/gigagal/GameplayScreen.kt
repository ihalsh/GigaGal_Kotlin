package com.udacity.gamedev.gigagal

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.udacity.gamedev.gigagal.overlays.GameOverOverlay
import com.udacity.gamedev.gigagal.overlays.GigaGalHud
import com.udacity.gamedev.gigagal.overlays.OnscreenControls
import com.udacity.gamedev.gigagal.overlays.VictoryOverlay
import com.udacity.gamedev.gigagal.util.Assets
import com.udacity.gamedev.gigagal.util.ChaseCam
import com.udacity.gamedev.gigagal.util.Constants.BACKGROUND_COLOR
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_END_DURATION
import com.udacity.gamedev.gigagal.util.Constants.WORLD_SIZE
import com.udacity.gamedev.gigagal.util.LevelLoader
import com.udacity.gamedev.gigagal.util.Utils
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.log.info
import ktx.log.logger

class GameplayScreen(
        private val spriteBatch: SpriteBatch = SpriteBatch(),
        private var levelEndOverlayStartTime: Long = 0,
        private val viewport: ExtendViewport = ExtendViewport(WORLD_SIZE, WORLD_SIZE),
        private var level: Level = LevelLoader.load("Level1", viewport),
        private var chaseCam: ChaseCam = ChaseCam(viewport.camera, level.gigaGal),
        private val hud: GigaGalHud = GigaGalHud(),
        private val victoryOverlay:VictoryOverlay = VictoryOverlay(),
        private val gameOverOverlay: GameOverOverlay = GameOverOverlay(),
        private val onscreenControls: OnscreenControls = OnscreenControls(level.gigaGal)) : KtxScreen {

    override fun show() {
        // Use Gdx.input.setInputProcessor() to send touch events to onscreenControls
        /* if (onMobile()) */Gdx.input.inputProcessor = onscreenControls

        onscreenControls.init()
        startNewLevel()
    }

    private fun onMobile(): Boolean = Gdx.app.type == Application.ApplicationType.Android
            || Gdx.app.type == Application.ApplicationType.iOS

    private fun startNewLevel() {
        level = LevelLoader.load("Level1", viewport)

//        val levelName = Constants.LEVELS [MathUtils.random(Constants.LEVELS.length - 1)];
//        level = LevelLoader.load(levelName)
        chaseCam = ChaseCam(viewport.camera, level.gigaGal)
        onscreenControls.gigaGal = level.gigaGal
        resize(Gdx.graphics.width, Gdx.graphics.height)
    }

    private fun renderLevelEndOverlays(batch: SpriteBatch) {

        if (level.victory) {

            if (levelEndOverlayStartTime == 0L) {
                levelEndOverlayStartTime = TimeUtils.nanoTime()
                victoryOverlay.init()
            }

            victoryOverlay.render(batch)

            if (Utils.timeSinceInSec(levelEndOverlayStartTime) > LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0L
                levelComplete()
            }
        }

        // Repeat the level victory logic to display the game over screen and call levelFailed()
        if (level.gameOver) {

            if (levelEndOverlayStartTime == 0L) {
                levelEndOverlayStartTime = TimeUtils.nanoTime()
                gameOverOverlay.init()
            }
            gameOverOverlay.render(batch)

            if (Utils.timeSinceInSec(levelEndOverlayStartTime) > LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0L
                levelFailed()
            }
        }

    }

    private fun levelFailed() = startNewLevel()

    private fun levelComplete() = startNewLevel()

    override fun render(delta: Float) {

        level.update(delta)
        chaseCam.update(delta)
        viewport.apply()

        clearScreen(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b)

        with(spriteBatch) {
            // Set the SpriteBatch's projection matrix
            projectionMatrix = viewport.camera.combined

            level.render(this)

            // Render the HUD
            hud.render(this,
                    lives = level.gigaGal.lives,
                    score = level.score,
                    ammo = level.gigaGal.ammoCount)

            renderLevelEndOverlays(this)


            // onMobile() turn off the controls when not on a mobile device
            /*if (onMobile())*/ onscreenControls.render(this)
        }
    }


    override fun resize(width: Int, height: Int) {
        // Update the viewports
        viewport.update(width, height, true)
        level.viewport.update(width, height, true)
        hud.viewport.update(width, height, true)
        victoryOverlay.viewport.update(width, height, true)
        gameOverOverlay.viewport.update(width, height, true)
        onscreenControls.viewport.update(width, height, true)
        onscreenControls.init()
        chaseCam.camera = level.viewport.camera
    }

    override fun dispose() {
        // Dispose of the Assets instance
        Assets.dispose()

        // Dispose of the SpriteBatch
        spriteBatch.dispose()
    }

    companion object {
        val logger = logger<GameplayScreen>()
    }
}
