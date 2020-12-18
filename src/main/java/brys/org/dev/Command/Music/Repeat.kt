package brys.org.dev.Command.Music

import brys.org.dev.Music_Handles.Manager.PlayerManager
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class Repeat : Command(){
    override fun execute(event: CommandEvent?) {
        val playerManager = PlayerManager.instance
        val musicManager = event?.guild?.let { playerManager!!.getGuildMusicManager(it, event.textChannel, event.author) }
        val player = musicManager?.audioPlayer
        val s = musicManager?.scheduler
        if (player?.playingTrack == null) {
            event?.reply("Nothing is playing right now, thus no track can be looped")
            return
        }
        if (s?.isRepeating()!!) {
           s.setRepeating(false)
            event.reply("Track `${player.playingTrack?.info?.title}` is **not** repeating." )
            return
        }
        else if (event.member.voiceState?.channel != event.guild.audioManager.connectedChannel) {
            event.reply("You must be in the same channel as the bot to use this command!")
            return
        }
        else if (!s.isRepeating()){
           s.setRepeating(true)
            event.reply("Track `${player.playingTrack?.info?.title}` is **now** repeating" )
            return
        }
    }
    init {
name = "repeat"
        cooldown = 3
        cooldownScope = CooldownScope.GUILD
        guildOnly = true
    }
}