/**
 * Copyright (c) 2020-
 *Creative Commons.
 *You must give attribution to the creator ( Brys ) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package src.Authenicator

import io.github.cdimascio.dotenv.Dotenv

object AUTH {
    private val dotenv = Dotenv.load()
    operator fun get(key: String): String? {
        return dotenv[key.toUpperCase()]
    }
}