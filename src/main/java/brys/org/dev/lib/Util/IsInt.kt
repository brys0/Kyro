package brys.org.dev.lib.Util

import java.lang.NumberFormatException

fun IsInt(args: String): Boolean {
    return try {
        args.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}