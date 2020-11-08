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
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import src.Music_Handles.Manager.PlayerManager.Companion.instance
import java.util.concurrent.TimeUnit


class Volume internal constructor() : Command()
{
    private var waiter: EventWaiter? = null
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
            event.channel.sendMessageFormat("Current volume `%s`. Would you like to change it?", musicManager.audioPlayer.volume).queue()
            waiter?.waitForEvent(MessageReceivedEvent::class.java,  // make sure it's by the same user, and in the same channel, and for safety, a different message
                    { e: MessageReceivedEvent -> e.author == event.author && e.channel == event.channel && e.message != event.message },  // respond, inserting the name they listed into the response
                    { e: MessageReceivedEvent -> if (e.message.contentRaw == "no") {
                        event.reply("Ok, returning now..")
                        return@waitForEvent
                    } else if (e.message.contentRaw == "yes"){
                        event.reply("Ok, what volume level do you want?")
                        waiter!!.waitForEvent(MessageReceivedEvent::class.java,
                                {e: MessageReceivedEvent -> e.author == event.author && e.channel == event.channel && e.message != event.message },
                                { e: MessageReceivedEvent ->
                                    try {
                                        args[0].toInt()
                                    } catch (e: java.lang.NumberFormatException) {
                                        event.reply("The response you gave contained no number.")
                                        return@waitForEvent
                                    }
                                    val number = args[0].toInt()
                                    if (number > 200) {
                                        event.reply("Make sure the number you are increasing it is lower than 200")
                                        return@waitForEvent
                                    }
                                    if (number > 10) {
                                        event.reply("The number must be more than 10.")
                                        return@waitForEvent
                                    }
                                    musicManager.audioPlayer.volume = number
                                    event.channel.sendMessageFormat("Volume is now `%s` ", number).queue()
                                })
                    } },  // if the user takes more than a minute, time out
                    1, TimeUnit.MINUTES) { event.reply("Sorry, you took too long.") }
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
        waiter = waiter;
        name = "volume"
        aliases = arrayOf("volume", "vol", "v")
        help = "Sets the volume 10 to 200"

    }
}