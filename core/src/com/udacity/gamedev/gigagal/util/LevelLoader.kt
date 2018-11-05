package com.udacity.gamedev.gigagal.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.viewport.Viewport
import com.udacity.gamedev.gigagal.Level
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_9PATCHES
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_COMPOSITE
import com.udacity.gamedev.gigagal.util.Constants.LEVEL_ERROR_MESSAGE
import ktx.log.info
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File

object LevelLoader {

    fun load(levelName: String, viewport: Viewport): Level {

        val level = Level(viewport = viewport)

        // Construct the path to the level file
        val path = "${Constants.LEVEL_DIR}${File.separator}$levelName.${Constants.LEVEL_FILE_EXTENSION}"

        try {

            // Get the level FileHandle object using Gdx.files.internal
            val file = Gdx.files.internal(path)

            // Create a new JSONParser and get the root JSONObject by parsing the level file
            val rootJsonObject = JSONParser().parse(file.reader()) as JSONObject

            info { "${rootJsonObject.keys}" }

            // Get the 'composite' object within the rootJsonObject
            val composite = rootJsonObject[LEVEL_COMPOSITE] as JSONObject

            info { "${composite.keys}" }

            // JSONArray behind the LEVEL_9PATCHES key
            val platformArray = composite[LEVEL_9PATCHES] as JSONArray

            // Get the first platform in the array
            val platform = platformArray[0] as JSONObject

            info { "${platform.keys}" }


        } catch (ex: Exception) {

            error { "${ex.message}\n$LEVEL_ERROR_MESSAGE" }
        }
        return level
    }
}
