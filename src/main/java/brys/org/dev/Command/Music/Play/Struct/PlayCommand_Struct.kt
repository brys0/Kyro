/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package brys.org.dev.Command.Music.Play.Struct


import brys.org.dev.Authenicator.AUTH
import brys.org.dev.Command.Music.API.Player
import brys.org.dev.Command.Music.API.Spotify
import brys.org.dev.Command.Music.API.YouTubeBasic
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import brys.org.dev.Command.Music.API.YouTube
import brys.org.dev.lib.Util.isUrl

class PlayCommand_Struct() {
    fun play_struct(event: CommandEvent) {
        val c = event.channel
        val membervcstate = event.member?.voiceState
        val membervcchannel = membervcstate!!.channel
        val args = listOf(event.args)
        val audioManager = event.guild.audioManager

        if (args.isEmpty() ) {
            return c.sendMessage("A song name or url is required!").queue()
        }
        if (!membervcstate.inVoiceChannel()) {
            return c.sendMessage("I can't see you in a voice channel I can join `ERR: NULL VOICE_CHANNEL`").queue()
        }
        if (!event.guild.selfMember.hasPermission(Permission.VOICE_CONNECT)) {
            return c.sendMessage("I don't have permission to speak in this voice channel `ERR: MISSING_PERMISSION`").queue()
        }
        if (!event.guild.selfMember.hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            return c.sendMessage("I don't have permission to use embeds `ERR: MISSING_PERMISSION`").queue()
        }
        if (!event.guild.selfMember.hasPermission(Permission.VOICE_SPEAK)) {
            return c.sendMessage("I don't have permission to speak in this voice channel `ERR: MISSING_PERMISSION`").queue()
        }
        if (!audioManager.isConnected) {
            try {
                audioManager.openAudioConnection(membervcchannel)
                c.sendMessageFormat("\uD83D\uDD0C Trying connect to `%s`", membervcchannel!!.name).queue()
                        audioManager.isSelfDeafened = true

            } catch (audiomanex: Exception) {
                if (audiomanex == IllegalArgumentException()) {
                   return c.sendMessage(".-. I couldn't cwonnect :c").queue()
                }
                if (audiomanex == UnsupportedOperationException()) {
                   return c.sendMessage("uh oh looks like I bwoke something :3").queue()
                } else {
                   c.sendMessage("Pwease give me permissions >:3").queue()
                }
            }
        }
        if (event.args.isBlank()) {
            event.reactWarning()
            event.reply("You need to have search term e.x `-play no friends cadmium`")
            return
        }
        if (event.args.contains("/(?:https?:4\\/\\/)?open\\.spotify\\.com/".toRegex())) {
            if (event.args.contains("open.spotify.com/playlist")) {
                event.reply("Sorry but right now only single track spotify tracks work. This is due to some ratelimting issues with YouTube's Data API.")
                return
            } else {
                val api = Spotify.getTrack(event.args)
                val image = Spotify.getRawTrack(event.args)?.album?.images?.get(0)
                val url = Spotify.getRawTrack(event.args)?.id
                val ablumname = Spotify.getRawTrack(event.args)?.album?.name
                val pop = Spotify.getRawTrack(event.args)?.popularity
                if (url != null) {
                    if (image != null) {
                        if (ablumname != null) {
                            if (pop != null) {
                                YouTubeBasic.spotifyTrackQueued(java.lang.String.join(" ", api), event,image, url, ablumname, pop)
                            }
                        }
                    }
                }
                return
            }
        }
        if(event.args.contains("/http(?:s:(?:1(?:92\\.168|72\\.)|2001:)|:(?:192\\.168|2001:))|1(?:92\\.168|72\\.)|2001:?/".toRegex())) {
            event.reply("You can't play local files on the network or public ftp server files!")
            return
        }
        if (isUrl(java.lang.String.join(" ", args)) == true) {
            Player.queue(java.lang.String.join(" ", args), event.textChannel, event.author, false)
            return
        } else
        AUTH["youtube_key"]?.let { YouTube.query(java.lang.String.join(" ", args), 1, it) }?.let { Player.queue(it[1-1], event.textChannel, event.author, false) }
        }

    }

