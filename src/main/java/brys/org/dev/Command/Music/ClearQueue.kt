package brys.org.dev.Command.Music

import brys.org.dev.Music_Handles.Manager.PlayerManager
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent


class ClearQueue: Command() {
    override fun execute(event: CommandEvent?) {
        val manager = PlayerManager.instance
        val musicm = event?.guild?.let { manager?.getGuildMusicManager(it, event.textChannel, event.author) }
        val queue = musicm?.scheduler?.queue
        val player = musicm?.audioPlayer
        val s = musicm?.scheduler
        val audioManager = event?.guild?.audioManager
        val membervcstate = event?.member?.voiceState
        val membervcchannel = membervcstate!!.channel
        if (audioManager?.isConnected == false) {
            event.reply("I must be in a voice channel.")
            return
        }
        if(membervcchannel == null || membervcchannel != audioManager?.connectedChannel) {
            event.reply("You need to be connected to the same channel!")
            return
        }
        player?.destroy()
        s?.queue?.clear()
        audioManager.closeAudioConnection()
        event.reply("\uD83D\uDCA5 | Queue was cleared!")
    }
    init {
        name = "clear"
        aliases = arrayOf("clear","stop","die")
        cooldown = 5
    }
}