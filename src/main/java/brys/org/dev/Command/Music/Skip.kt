/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package brys.org.dev.Command.Music

import brys.org.dev.Music_Handles.Manager.PlayerManager.Companion.instance
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Role
import java.awt.Color
import java.lang.Exception
import java.lang.IllegalStateException

/**
 * A skip command default role needed to skip is DJ
 * @see Role
 * @property name = "skip"
 * @property aliases = "s"
 * @property help = "Skips the currently playing track"
 */
class Skip : Command() {
    override fun execute(event: CommandEvent) {
        try {
        val channel = event.textChannel
        val playerManager = instance
        val GMM = playerManager!!.getGuildMusicManager(event.guild, event.textChannel, event.author)
        val s = GMM.scheduler
        val player = GMM.audioPlayer
        val membervcstate = event.member?.voiceState
        val membervcchannel = membervcstate!!.channel
        val self = event.selfMember
        val selfVoiceState = self.voiceState
        if (s?.isRepeating()!!) {
            event.reply("I cannot skip a currently repeating track!")
            return
        }

        if (event.guild.roles.stream().noneMatch { r: Role -> r.name.equals("DJ", ignoreCase = true) }) {
            event.reply("Seems like a DJ role didn't exist so i've created one for you.")
            event.getGuild().createRole().setName("DJ").setColor(Color.CYAN).setPermissions(0L).reason("Automatic DJ Role Creation. (Powered by KYRO)").queue()
        }
        if (event.member.roles.stream().noneMatch { r: Role -> r.name.equals("DJ", ignoreCase = true) or !event.isOwner or !event.member.hasPermission(Permission.ADMINISTRATOR)}) {
            event.textChannel.sendMessage("You need the role `DJ` for that!").queue()
            return
        }
        if (membervcchannel == null) {
            event.reply("Join a voice channel first.")
            return
        }

        if (membervcchannel != selfVoiceState?.channel) {
            event.reply("You need to be in the same channel (`${event.selfMember.voiceState?.channel?.name}`) to skip!")
            return
        }
        if (player.playingTrack == null) {
            try {
                channel.sendMessage("Can't skip when no track is playing!").queue()
                return
            } catch (e: Exception) {event.reply("a")}
        }

        val track = player.playingTrack.info.title
        try {
            s.nextTrack()
        } catch (e: IllegalStateException) {
            s.nextTrack()
            return
        }
            channel.sendMessage("Skipped **$track**").queue()
    } catch (e: Exception) {
            event.reply("Internal Error")
        }
    }
    init {
        name = "skip"
        aliases = arrayOf("s")
        help = "Skips the currently playing track"
        cooldown = 3
        cooldownScope = CooldownScope.GUILD
    }
}