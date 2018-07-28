package com.ladbrokescoral.lcgcore.utils.tests

import android.util.Log
import com.avi.taxez.base.App
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type

/**
 * Created by avi.barel on 05/03/2018.
 */
class MockUtils {

    companion object {
        private val GSON = GsonBuilder()
            .setLenient()
            .create()

        fun <T> loadJson(path: String, type: Type): T {
            var bufferedReader: BufferedReader? = null
            try {

                bufferedReader = BufferedReader(InputStreamReader(App.context.assets.open(path)))
                return GSON.fromJson(bufferedReader, type)
            } catch (e: JsonSyntaxException) {
                Log.e("MockUtils", "Error: " + e.message)
                throw IllegalArgumentException("Can't convert '$path' to class: $type")
            } finally {
                bufferedReader?.close()
            }

        }

    }

}