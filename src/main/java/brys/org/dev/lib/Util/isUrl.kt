/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package brys.org.dev.lib.Util

import java.net.MalformedURLException
import java.net.URL

fun isUrl(input: String): Boolean? {
    return try {
        URL(input)
        true
    } catch (ignored: MalformedURLException) {
        false
    }
}
