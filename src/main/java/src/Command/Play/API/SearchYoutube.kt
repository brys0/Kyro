/**
 * Copyright (c) 2020
*Creative Commons.
*You must give attribution to the creator (Bryson T.) of this work.
*Noncommercial
*This can not be used for commercial use or used to make a profit.
 */
package src.Command.Play.API

import com.github.ajalt.mordant.TermColors
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.mongodb.event.CommandEvent
import src.Authenicator.AUTH
import src.Music_Handles.Manager.PlayerManager.Companion.instance
import src.Util.isUrl
import java.lang.Exception

/**
 * Loads and puts your song in queue then sends a message for your song
 * @sample
 * @throws net.dv8tion.jda.api.exceptions.InsufficientPermissionException if it doesn't have send message sending permission
 * @throws java.io.IOException If track fails to load
 */

class SearchYoutube {
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
            manager?.loadAndPlay(event.textChannel, ytSearched)
            c.sendMessage("\uD83D\uDD0D Searching YouTube for your song `" + args.toString().replace("[", "").replace("]", "") + "`").queue()
            return

        }
        val manager = instance
        manager!!.loadAndPlay(event.textChannel, input)
        println(t.brightBlue("Searching for song ${t.reset}${t.red(input)}${t.reset}${t.brightBlue} on guild ${event.guild.name}${t.reset}"))
    }
private fun YoutubeSearch(input: String): String? {
    try {
        val results = youTube!!.search()
                .list("id,snippet")
                .setQ(input)
                .setMaxResults(1L)
                .setType("video")
                .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                .setKey(AUTH.get("youtube_key"))
                .execute()
                .items
        if (!results.isEmpty()) {
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
