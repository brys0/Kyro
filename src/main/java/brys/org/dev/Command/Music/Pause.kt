package brys.org.dev.Command.Music

import brys.org.dev.Kyro.KyroUtils
import brys.org.dev.Music_Handles.Manager.PlayerManager
import brys.org.dev.Settings.GuildSettings
import brys.org.dev.lib.Util.RequiredUtil.KyroCommand
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Role

class Pause: KyroCommand() {
    override fun execute(event: CommandEvent) {
        val manager = PlayerManager.instance
        val musicm = event.guild?.let { manager?.getGuildMusicManager(it, event.textChannel, event.author) }
        val player = musicm?.audioPlayer
        val djrole = event.guild.getRoleById(GuildSettings.findSetting(event.guild.id)?.get("DJRole").toString())
        if (player?.isPaused == true) {
            player.isPaused = false
           return event.reply("Track `${player.playingTrack.info.title} has been resumed at **${brys.org.dev.lib.Util.AudioUtil.formatTime(player.playingTrack.info.length)}**")
        } else if (player?.isPaused == false) {
              player.isPaused = true
            return event.reply("Track `${player.playingTrack.info.title} has been paused at **${brys.org.dev.lib.Util.AudioUtil.formatTime(player.playingTrack.info.length)}**")
        }
    }
    init {
        playingReq = true
        djReq = true
        name = "pause"
    }
}