package brys.org.dev.src.Command.Music.Lyric.API

import brys.org.dev.Authenicator.AUTH
import net.explodingbush.ksoftapi.KSoftAPI
import net.explodingbush.ksoftapi.entities.Lyric
import java.lang.IndexOutOfBoundsException

/**
 * An object that provides different endpoints for Ksoft API
 */
object KSoft {
    /**
     * Searches for lyrics with given track name
     */
    fun searchLyrics(trackName: String): Lyric? {
        val api = KSoftAPI(brys.org.dev.Authenicator.AUTH["KSOFT"])
        val results: MutableList<Lyric> = api.lyrics.search(trackName).setLimit(1).execute()
        try {
            return results[0]
        } catch (e: IndexOutOfBoundsException) {
            println("lyrics not found")
        }
      return null
    }
    // add more as needed...
}