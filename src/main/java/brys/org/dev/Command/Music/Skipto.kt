package brys.org.dev.Command.Music

import brys.org.dev.Music_Handles.Manager.PlayerManager
import brys.org.dev.lib.Util.IsInt
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Role
import java.awt.Color

class Skipto: Command() {
    override fun execute(event: CommandEvent) {
        val channel = event.textChannel
        val playerManager = PlayerManager.instance
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
            event.getGuild().createRole().setName("DJ").setColor(Color.CYAN).queue()
        }
        if (event.member.roles.stream().noneMatch { r: Role -> r.name.equals("DJ", ignoreCase = true) or !event.isOwner or !event.member.hasPermission(Permission.ADMINISTRATOR) }) {
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
            channel.sendMessage("Can't skip when no track is playing!").queue()
            return
        }
        val index = 0
        if (!IsInt(event.args)) {
            event.reply("Command contained no valid number!")
            return
        }
else
        s.skip(index-1)
        event.reply("Skipped to **${s.queueAsLinked()[0].info.title}**")
        player.stopTrack()

    }
    init {
        name = "skipto"
        aliases = arrayOf("st","skipat")
    }
}