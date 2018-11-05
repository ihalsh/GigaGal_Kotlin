package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.Viewport
import com.udacity.gamedev.gigagal.Level
import com.udacity.gamedev.gigagal.entities.Enemy
import com.udacity.gamedev.gigagal.entities.Platform
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_9PATCHES
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_COMPOSITE
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_ENEMY_TAG
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_ERROR_MESSAGE
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_HEIGHT_KEY
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_IDENTIFIER_KEY
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_WIDTH_KEY
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_X_KEY
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_Y_KEY
import ktx.log.info
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

            loadPlatforms(jsonPlatformArray, level)


        } catch (ex: Exception) {

            logger.error(ex) { LEVEL_ERROR_MESSAGE }
        }
        return level
    }

    private fun loadPlatforms(array: JSONArray, level: Level) {

        val platformArray = Array<Platform>()

        for (jsonObject in array) {

            val platformObject = jsonObject as JSONObject
            val x = (platformObject[LEVEL_X_KEY] as Number? ?: 0).toFloat()
            val y = (platformObject[LEVEL_Y_KEY] as Number? ?: 0).toFloat()
            val width = (platformObject[LEVEL_WIDTH_KEY] as Number).toFloat()
            val height = (platformObject[LEVEL_HEIGHT_KEY] as Number).toFloat()

            // Make a new platform with the dimensions we loaded
            val platform = Platform(x, y, width, height)

            // Add the platform to the platformArray
            platformArray.add(platform)

            // log the location and dimensions of the platform
            info { "Platform added: $x, $y, width=$width, height=$height" }

            // Get the platform identifier
            val identifier = platformObject[LEVEL_IDENTIFIER_KEY] as String?

            // Check if the platform identifier equals the LEVEL_ENEMY_TAG
            if (identifier == LEVEL_ENEMY_TAG) level.enemies.add(Enemy(platform))
        }

        // Sort the platform array by descending position of the top of the platform
        // We're doing this so lower platforms aren't hidden by higher platforms
        platformArray.sort { o1, o2 -> o2.top.compareTo(o1.top) }

        // Add all the platforms from platformArray to the level
        level.platforms.addAll(platformArray)

    }
}
