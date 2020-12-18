/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package brys.org.dev.Command.Owner


import brys.org.dev.Command.Music.NowPlaying
import brys.org.dev.Command.Music.Play.play
import brys.org.dev.Command.Music.Queue
import brys.org.dev.Command.Music.Skip
import brys.org.dev.Command.Music.Volume
import brys.org.dev.Kyro.KyroUtils
import brys.org.dev.lib.Util.RequiredUtil.KyroCommand
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder

class Debug : KyroCommand() {
    override fun execute(event: CommandEvent?) {
        val skipCount = event?.client?.getCommandUses(Skip())
        val playCount = event?.client?.getCommandUses(play())
        val queueCount = event?.client?.getCommandUses(Queue())
        val volumeCount = event?.client?.getCommandUses(Volume())
        val npCount = event?.client?.getCommandUses(NowPlaying())
        val debugCount = event?.client?.getCommandUses(Debug())
        val debugEmbed =
                EmbedBuilder()
                        .setTitle("Debug")
                        .addField("Skip Count", "`$skipCount`", false)
                        .addField("Play Count", "`$playCount`", false)
                        .addField("Queue.kt Count", "`$queueCount`", false)
                        .addField("Volume Count", "`$volumeCount`", false)
                        .addField("NowPlaying Count", "`$npCount`", false)
                        .addField("Debug Count", "`$debugCount`", false)
                        .addBlankField(false)
                        .addField("Guilds", "${event?.jda?.guilds?.size}", false)
                    .addField("CPU Usage", KyroUtils.procUsage().toString() + "%", false)
        event?.reply(debugEmbed.build())
    }

    init {
        name = "debug"
        ownerCommand = true
        guildOnly = false
    }
}