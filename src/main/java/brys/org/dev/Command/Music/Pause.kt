package brys.org.dev.Command.Music

import brys.org.dev.Kyro.KyroUtils
import brys.org.dev.Music_Handles.Manager.PlayerManager
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class Pause: Command() {
    override fun execute(event: CommandEvent) {
        val manager = PlayerManager.instance
        val musicm = event.guild?.let { manager?.getGuildMusicManager(it, event.textChannel, event.author) }
        val queue = musicm?.scheduler?.queue
        val player = musicm?.audioPlayer

        if (player?.playingTrack == null) {
            KyroUtils.error(event, ":KyroX:", "There must be a **playing** track!")
            return
        }
        if (player.isPaused) {
            player.isPaused = false
        }
    }
}