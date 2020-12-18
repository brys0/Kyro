package brys.org.dev.Command.Music.API

import brys.org.dev.Authenicator.AUTH
import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.models.SimpleAlbum
import com.adamratzman.spotify.spotifyAppApi
import com.jagrosh.jdautilities.command.CommandEvent

/**
 * Object that provides different spotify endpoints
 * ##### Different functions:
 * * [getTrack]
 * * [getRawTrack]
 * * [getTop]
 * * [getPlaylistTracks]
 * * [getAPI]
 */
object Spotify {
    val uniapi = AUTH["spotify_public"]?.let { AUTH["spotify_private"]?.let { it1 -> spotifyAppApi(it, it1).build() } }
    /**
     * Takes a trackname string and returns the track name to be searched by [YouTube]
     */
    fun getTrack(args: String): String? {
        val parseid = args.replace("https://open.spotify.com/track/", "")
        val returns = uniapi?.tracks?.getTrack(parseid)?.complete()?.name
        return returns
    }
    /**
     * Takes a trackname string and returns a raw modeled track To be parsed elsewhere
     */
    fun getRawTrack(args: String): com.adamratzman.spotify.models.Track? {
        val parseid = args.replace("https://open.spotify.com/track/", "")
        val returns = uniapi?.tracks?.getTrack(parseid)?.complete()
        return returns
    }
    /**
     * Gets top releases on spotify and returns [SimpleAlbum]
     */
    fun getTop(): SimpleAlbum? {
        val returns = uniapi?.browse?.getNewReleases(1)?.complete()
        return returns?.get(0)
    }
    /**
     * Gets and calls [YouTube] to execute searching of each track name
     */
    fun getPlaylistTracks(args: String, event: CommandEvent): String {
        val parseid = args.replace("https://open.spotify.com/playlist/", "")
        val returns = uniapi?.playlists?.getPlaylist(parseid)?.complete()
        val embedtest = ""
        for (i in 0 until returns?.tracks?.size!!) {
                val track = returns.tracks[i]
               val trackidName =  track.track?.id?.let { getTrack(it) }
                if (trackidName != null) {
                    YouTube.spotifySearched("$trackidName",event)
                }
                return embedtest
        }
        return "BRK"
    }
    /**
     * Returns the insitated API
     */
    fun getAPI(): SpotifyAppApi? {
      return uniapi
    }

}