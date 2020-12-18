/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package brys.org.dev.Command.Music.Play.Struct


import brys.org.dev.Command.Music.API.Spotify
import brys.org.dev.Command.Music.API.YouTube
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException

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
            return c.sendMessage(">~< I can't see you in a channel I can connect to UnU").queue()
        }
        if (!event.guild.selfMember.hasPermission(Permission.VOICE_CONNECT)) {
            return c.sendMessage("OwO Can i pwease have the voice connect permission :point_right: :point_left:").queue()
        }
        if (!event.guild.selfMember.hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            return c.sendMessage(">n< looks wike I can't use my adorabwle embeds..").queue()
        }
        if (!event.guild.selfMember.hasPermission(Permission.VOICE_SPEAK)) {
            return c.sendMessage(":c someone stopped me from spweaking ono").queue()
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
                                YouTube.spotifyTrackQueued(java.lang.String.join(" ", api), event,image, url, ablumname, pop)
                            }
                        }
                    }
                }
                return
            }
        }

        if (event.isOwner) {
            YouTube.ytSearched(java.lang.String.join(" ", args), event)
            return
        }
        if(event.args.contains("/http(?:s:\\/\\/(?:1(?:92\\.168|72\\.)|2001:)|:\\/\\/(?:192\\.168|2001:))|1(?:92\\.168|72\\.)|2001:?/".toRegex())) {
            event.reply("You can't play local files on the network or public ftp server files!")
            return
        }
        if (event.args.startsWith("--force")) {
            YouTube.playNowYtSearched(java.lang.String.join(" ", args.toString().replace("--force", "")), event)
            return
        }
            YouTube.ytSearched(java.lang.String.join(" ", args), event)
        }
    }

