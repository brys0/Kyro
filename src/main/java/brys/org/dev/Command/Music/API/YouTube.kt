/**
 * Copyright (c) 2020
*Creative Commons.
*You must give attribution to the creator (Bryson T.) of this work.
*Noncommercial
*This can not be used for commercial use or used to make a profit.
 */
package brys.org.dev.Command.Music.API

import brys.org.dev.Music_Handles.Manager.PlayerManager.Companion.instance
import brys.org.dev.lib.Util.isUrl
import com.github.ajalt.mordant.TermColors
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.mongodb.event.CommandEvent

import com.adamratzman.spotify.models.SpotifyImage
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import java.lang.Exception
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color


/**
 * Loads and puts your song in queue then sends a message for your song
 * @sample
 * @throws net.dv8tion.jda.api.exceptions.InsufficientPermissionException if it doesn't have send message sending permission
 * @throws java.io.IOException If track fails to load
 */

object YouTube {
    val t = TermColors()
    private val youTube: YouTube?
    /**
     * You must compile the search string like such
     * @sample java.lang.String.join(" ", args)
     */
    fun ytSearched(input: String, event: com.jagrosh.jdautilities.command.CommandEvent) {
        /**
         * Grabbing needed event details
         * @sample CommandEvent
         */
        val c = event.channel
        val args = listOf(event.args)
        if (!isUrl(input)!!) {
            val manager = instance
            val ytSearched = YoutubeSearch(input)
            if (ytSearched == null) {
                c.sendMessage("I found no results, or vaild url for your argument $input").queue()
                return
                /**
                 * Checks if it can find the track
                 * @throws NullPointerException
                 */
            }
            manager?.loadAndPlay(event.textChannel, ytSearched, event.author)
            c.sendMessage("\uD83D\uDD0D Searching YouTube for your song `$input`").queue()
            return

        }
        val manager = instance
        manager!!.loadAndPlay(event.textChannel, input, event.author)
    }
    fun playNowYtSearched(input: String, event: com.jagrosh.jdautilities.command.CommandEvent) {
        val c = event.channel
        val args = listOf(event.args)

        if (!isUrl(input)!!) {
            val manager = instance
            val ytSearched = YoutubeSearch(input.removeSurrounding(" "))
            val musicm = event.guild?.let { manager?.getGuildMusicManager(it, event.textChannel, event.author) }
            val s = musicm?.scheduler
            val player = musicm?.audioPlayer
            if (ytSearched == null) {
                c.sendMessage("I found no results, or valid url for your argument $input").queue()
                return
                /**
                 * Checks if it can find the track
                 * @throws NullPointerException
                 */
            }
            s?.queue?.clear()
            player?.destroy()
            manager?.loadAndPlay(event.textChannel, ytSearched, event.author)
            c.sendMessage("\uD83D\uDD0D (Playing Now) - Searching YouTube for your song `$input`").queue()
            return

        }
        val manager = instance
        val musicm = event.guild?.let { manager?.getGuildMusicManager(it, event.textChannel, event.author) }
        val s = musicm?.scheduler
        val player = musicm?.audioPlayer
        s?.queue?.clear()
        player?.destroy()
        manager!!.loadAndPlay(event.textChannel, input, event.author)
    }
    fun spotifySearched(input: String, event: com.jagrosh.jdautilities.command.CommandEvent) {

        /**
         * Grabbing needed event details
         * @sample CommandEvent
         */
        val c = event.channel
        val args = listOf(event.args)
        if (!isUrl(input)!!) {
            val manager = instance
            val ytSearched = YoutubeSearch(input)
                ?: return
            /**
             * Checks if it can find the track
             * @throws NullPointerException
             */


            return
        }
    }
    fun spotifyTrackQueued(input: String, event: com.jagrosh.jdautilities.command.CommandEvent, thumbnail: SpotifyImage, spotifyurl: String?, name: String, pop: Int?) {
        val manager1 = instance
        val ytSearched = YoutubeSearch(input)
        val musicm = event.guild.let { manager1?.getGuildMusicManager(it, event.textChannel, event.author) }
        if (musicm != null) {
            instance?.playerManager?.loadItemOrdered(musicm, ytSearched, object : AudioLoadResultHandler {
                override fun trackLoaded(track: AudioTrack) {
                    instance!!.play(musicm, track, event.textChannel)
                    val spotifyEmbed = EmbedBuilder()
                        .setTitle(input, "https://open.spotify.com/track/$spotifyurl")
                        .setThumbnail(thumbnail.url)
                        .addField("Album Name", name, true)
                        .setFooter("Spotify Track", "https://cdn.discordapp.com/emojis/788597282072821790.png?v=1")
                        .setColor(Color.decode("#1DB954"))
                        .addField("Popularity", pop.toString(), true).build()
                    event.reply(spotifyEmbed)
                }


                override fun playlistLoaded(playlist: AudioPlaylist?) {
                    TODO("Not yet implemented")
                }

                override fun noMatches() {
                    event.reply("No Matches.")
                }

                override fun loadFailed(exception: FriendlyException?) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
fun YoutubeSearch(input: String): String? {
    try {
        val results = youTube!!.search()
                .list("id,snippet")
                .setQ(input)
                .setMaxResults(1L)
                .setType("video")
                .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                .setKey(brys.org.dev.Authenicator.AUTH.get("youtube_key"))
                .execute()
                .items
        if (results.isNotEmpty()) {
            val videoId = results[0].id.videoId
            return "https://www.youtube.com/watch?v=$videoId"
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}
init {
    var temp: YouTube? = null
    try {
        temp = YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null).setApplicationName("Kyro").build()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    youTube = temp
}
}
