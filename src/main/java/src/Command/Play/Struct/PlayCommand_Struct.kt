/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package src.Command.Play.Struct


import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import src.Command.Play.API.SearchYoutube
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
                c.sendMessageFormat("\uD83D\uDD0C trying connect to `%s`", membervcchannel!!.name).queue()
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
            SearchYoutube().ytSearched(java.lang.String.join(" ", args), event)
        }
    }

