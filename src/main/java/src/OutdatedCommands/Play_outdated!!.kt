/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */
package src.OutdatedCommands

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import src.Authenicator.AUTH
import src.Music_Handles.Manager.PlayerManager.Companion.instance
import java.net.MalformedURLException
import java.net.URL

class `play_outdated!!` : Command() {
    private val youTube: YouTube?
    override fun execute(event: CommandEvent) {
        val channel = event.channel
        val audioManager = event.guild.audioManager
        val MVS = event.member.voiceState
        val MC = MVS!!.channel
        val args = listOf(event.args)
        if (args.isEmpty()) {
            channel.sendMessage("No url").queue()
            return
        }
        if (!MVS.inVoiceChannel()) {
            channel.sendMessage("You are not connected to a voice channel, or i cannot access it.").queue()
            return
        }
        if (!event.guild.selfMember.hasPermission(Permission.VOICE_CONNECT)) {
            channel.sendMessage("Missing permission 'VOICE_CONNECT'").queue()
            return
        }
        if (!event.guild.selfMember.hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            channel.sendMessage("Missing permission 'MESSAGE_EMBED_LINKS").queue()
            return
        }
        if (!audioManager.isConnected) {
            audioManager.openAudioConnection(MC)
            channel.sendMessageFormat("`\uD83D\uDD0C` Attempting connection to: `%s`", MC!!.name).queue()
            audioManager.isSelfDeafened = true
        }
        var input = java.lang.String.join(" ", args)
        if (!isUrl(input)) {
            val ytSearched = searchYoutube(input)
            if (ytSearched == null) {
                channel.sendMessageFormat("Youtube returned 0 hits for this string: `%s`", input).queue()
                return
            }
            channel.sendMessage("\uD83D\uDD0D Searching youtube for `" + args.toString().replace("[", "").replace("]", "") + "`").queue()
            input = ytSearched
        }
        val manager = instance
        manager!!.loadAndPlay(event.textChannel, input)
    }

    private fun isUrl(input: String): Boolean {
        return try {
            URL(input)
            true
        } catch (ignored: MalformedURLException) {
            false
        }
    }

    private fun searchYoutube(input: String): String? {
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
        name = "play.o"
        help = "Plays a song"
        guildOnly = true
        arguments = getArguments()
        cooldown = 3
        var temp: YouTube? = null
        try {
            temp = YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null).setApplicationName("Kyro").build()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        youTube = temp
    }
}