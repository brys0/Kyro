package brys.org.dev.Command.Music

import brys.org.dev.Music_Handles.Manager.PlayerManager
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import brys.org.dev.lib.Util.TimeConvertor
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime


class Seek: Command() {
    @ExperimentalTime
    override fun execute(event: CommandEvent?) {
        val playerManager = PlayerManager.instance
        val musicManager = event?.guild?.let { playerManager!!.getGuildMusicManager(it, event.textChannel, event.author) }
        val player = musicManager?.audioPlayer
        if (event?.args?.isBlank()!!) {
            event.reactWarning()
            event.reply("Unable to set position of no value.")
            return
        }
        if (player?.playingTrack == null) {
            event.reactWarning()
            event.reply("Unable to set position when no track is playing.")
            return
        }
        try {
            val time = brys.org.dev.lib.Util.TimeConvertor.parseTimeString(event.args)
        } catch (e: Exception) {
            return event.reply("The requested number was too high or wasn't a valid syntax try again with a number less than the duration of the song ex: `-seek 0:22`")
        }
        val time = brys.org.dev.lib.Util.TimeConvertor.parseTimeString(event.args)
        player.playingTrack.position = if (player.playingTrack.duration < time) player.playingTrack.duration else if (time < 0) 0 else time
        event.reply(" Seeking to `${formatTime(player.playingTrack.position)}` :fast_forward:")
    }

    init {
        arguments = "<HH:mm:ss>"
        name = "seek"
        aliases = arrayOf("setposition","position","se")
    }
    private fun formatTime(timeInMillis: Long): String {
        val hours = timeInMillis / TimeUnit.HOURS.toMillis(1)
        val minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1)
        val seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1)
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}