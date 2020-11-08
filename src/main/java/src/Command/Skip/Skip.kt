/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package src.Command.Skip

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Role
import src.Music_Handles.Manager.PlayerManager.Companion.instance
import java.awt.Color
import java.lang.NullPointerException

/**
 * A skip command default role needed to skip is DJ
 * @see Role
 * @property name = "skip"
 * @property aliases = "s"
 * @property help = "Skips the currently playing track"
 */
class Skip : Command() {

    override fun execute(event: CommandEvent) {
        val channel = event.textChannel
        val playerManager = instance
        val GMM = playerManager!!.getGuildMusicManager(event.guild)
        val s = GMM.scheduler
        val player = GMM.audioPlayer

        s.isRepeating = true
        if (event.guild.roles.stream().noneMatch { r: Role -> r.name.equals("DJ", ignoreCase = true) }) {
            event.reply("Seems like a DJ role didn't exist so i've created one for you.")
            event.guild.createRole().setName("DJ").setColor(Color.CYAN).queue()
        }
        if (event.member.roles.stream().noneMatch { r: Role -> r.name.equals("DJ", ignoreCase = true) or !event.isOwner or !event.member.hasPermission(Permission.ADMINISTRATOR) }) {
            event.textChannel.sendMessage("You need the role `DJ` for that!").queue()
            return
        }
        if (player.playingTrack == null) {
            channel.sendMessage("Can't skip when no track is playing!").queue()
            return
        }
            s.nextTrack()
            channel.sendMessage("Skipped.").queue()
    }
    init {
        name = "skip"
        aliases = arrayOf("s")
        help = "Skips the currently playing track"
    }
}