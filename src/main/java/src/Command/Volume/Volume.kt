/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package src.Command.Volume

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import src.Music_Handles.Manager.PlayerManager.Companion.instance


class Volume internal constructor() : Command()
{
    override fun execute(event: CommandEvent)
    {
        val MVS = event.member.voiceState
        val manager = event.guild.audioManager
        val playerManager = instance
        val musicManager = playerManager!!.getGuildMusicManager(event.guild)
        val args = listOf(event.args)
        if (!MVS!!.inVoiceChannel() && manager.isConnected)
        {
            event.reply("Please connect to a voice channel first that I can see.")
            return
        }
        if (event.args.isEmpty())
        {
            event.channel.sendMessageFormat("Current volume `%s`", musicManager.audioPlayer.volume).queue()
            return
        }


        try
        {
            args[0].toInt()
        } catch (e: NumberFormatException)
        {
            event.reply("The arguments for this command contained no number.")
            return
        }
        val number = args[0].toInt()
        if (number > 200)
        {
            event.reply("Make sure the number you are increasing it is lower than 200")
            return
        }
        if (number < 10)
        {
            event.reply("The number must be more than 10.")
            return
        }
        musicManager.audioPlayer.volume = number
        event.channel.sendMessageFormat("Volume is now `%s` ", number).queue()
    }

    init
    {
        name = "volume"
        aliases = arrayOf("volume", "vol", "v")
        help = "Sets the volume 10 to 200"

    }
}