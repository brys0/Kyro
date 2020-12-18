package brys.org.dev.lib.Util.AudioUtil

import com.jagrosh.jdautilities.command.CommandEvent
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.VoiceChannel

object AudioChecks {
    fun isPlaying(player: AudioPlayer): Boolean {
        return when (player.playingTrack) {
            null -> false
            is AudioTrack -> true
            else -> {
                false
            }
        }
    }
    fun memberConnected(e: CommandEvent): Boolean {
        val membervcstate = e.member.voiceState
        return when (membervcstate?.channel) {
            null -> false
            is VoiceChannel -> true
            else -> {
                false
            }
        }
    }
}