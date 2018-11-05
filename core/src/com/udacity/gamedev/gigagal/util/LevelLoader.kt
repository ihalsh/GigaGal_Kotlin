package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.Viewport
import com.udacity.gamedev.gigagal.Level
import com.udacity.gamedev.gigagal.entities.Enemy
import com.udacity.gamedev.gigagal.entities.Platform
import com.udacity.gamedev.gigagal.entities.Powerup
import com.udacity.gamedev.gigagal.util.Constants.EXIT_PORTAL_SPRITE_1
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_9PATCHES
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_COMPOSITE
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_ENEMY_TAG
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_ERROR_MESSAGE
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_HEIGHT_KEY
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_IDENTIFIER_KEY
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_IMAGENAME_KEY
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_IMAGES
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_WIDTH_KEY
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_X_KEY
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_Y_KEY
import com.udacity.gamedev.gigagal.util.Constants.POWERUP_SPRITE
import com.udacity.gamedev.gigagal.util.Constants.STANDING_RIGHT
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File

object LevelLoader {

    private val logger = ktx.log.logger<LevelLoader>()

    fun load(levelName: String, viewport: Viewport): Level {

        val level = Level(viewport = viewport)

        // Construct the path to the level file
        val path = "${Constants.LEVEL_DIR}${File.separator}$levelName.${Constants.LEVEL_FILE_EXTENSION}"

        try {

            // Get the level FileHandle object using Gdx.files.internal
            val file = Gdx.files.internal(path)

            // Create a new JSONParser and get the root JSONObject by parsing the level file
            val rootJsonObject = JSONParser().parse(file.reader()) as JSONObject

            // Get the 'composite' object within the rootJsonObject
            val composite = rootJsonObject[LEVEL_COMPOSITE] as JSONObject

            // JSONArray behind the LEVEL_9PATCHES key
            val jsonPlatformArray = composite[LEVEL_9PATCHES] as JSONArray

            // JSONArray behind the LEVEL_IMAGES key
            val jsonLevelImagesArray = composite[LEVEL_IMAGES] as JSONArray

            loadPlatforms(jsonPlatformArray, level)
            loadOther(jsonLevelImagesArray, level)


        } catch (ex: Exception) {

            logger.error(ex) { LEVEL_ERROR_MESSAGE }
        }
        return level
    }

    private fun loadPlatforms(array: JSONArray, level: Level) {

        val platformArray = Array<Platform>()

        for (jsonObject in array) {

            val platformObject = jsonObject as JSONObject
            val width = (platformObject[LEVEL_WIDTH_KEY] as Number).toFloat()
            val height = (platformObject[LEVEL_HEIGHT_KEY] as Number).toFloat()

            // Make a new platform with the dimensions we loaded
            val platform = Platform(
                    getJsonObjectPosition(jsonObject).x,
                    getJsonObjectPosition(jsonObject).y,
                    width,
                    height
            )

            // Add the platform to the platformArray
            platformArray.add(platform)

            // Get the platform identifier
            val identifier = platformObject[LEVEL_IDENTIFIER_KEY] as String?

            // Check if the platform identifier equals the LEVEL_ENEMY_TAG
            if (identifier == LEVEL_ENEMY_TAG) level.enemies.add(Enemy(platform))
        }

        // Sort the platform array by descending position of the top of the platform
        // We're doing this so lower platforms aren't hidden by higher platforms
        platformArray.sort { platform1, platform2 -> platform2.top.compareTo(platform1.top) }

        // Add all the platforms from platformArray to the level
        level.platforms.addAll(platformArray)
    }

    private fun loadOther(jsonLevelImagesArray: JSONArray, level: Level) {

        for (arrayObject in jsonLevelImagesArray) {
            val jsonObject = arrayObject as JSONObject

            //Adds powerups
            if (jsonObject[LEVEL_IMAGENAME_KEY] == POWERUP_SPRITE) {
                level.powerups.add(Powerup(getJsonObjectPosition(jsonObject)))
            }
            //Set exit portal position
            if (jsonObject[LEVEL_IMAGENAME_KEY] == EXIT_PORTAL_SPRITE_1) {
                level.exitPortal.position.set(getJsonObjectPosition(jsonObject))
            }

            //Set GigaGal's position
            if (jsonObject[LEVEL_IMAGENAME_KEY] == STANDING_RIGHT) {
                level.gigaGal.position.set(getJsonObjectPosition(jsonObject))
            }
        }
    }

    private fun getJsonObjectPosition(jsonObject: JSONObject): Vector2 =
            Vector2((jsonObject[LEVEL_X_KEY] as Number? ?: 0).toFloat(),
                    (jsonObject[LEVEL_Y_KEY] as Number? ?: 0).toFloat())
}
