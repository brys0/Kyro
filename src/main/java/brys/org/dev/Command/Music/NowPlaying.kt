/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package brys.org.dev.Command.Music

import brys.org.dev.Music_Handles.Manager.PlayerManager.Companion.instance
import com.github.ajalt.mordant.TermColors
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color
import java.lang.Exception
import java.util.concurrent.TimeUnit

class NowPlaying : Command() {
    override fun execute(event: CommandEvent)
    {
        val t = TermColors()
        val playerManager = instance
        val musicManager = playerManager!!.getGuildMusicManager(event.guild, event.textChannel, event.author)
        val player = musicManager.audioPlayer

       if (player.playingTrack == null) {
           event.reply("No track is playing right now. Try again with a playing track.\n" +
                   "Don't know how to play your favorite track? Just do `-play {track_name}`")
           return
       }
        try {
        val info = player.playingTrack.info
        val track = player.playingTrack
            val totalBlocks = 15
            val percent = track.position.toDouble() / track.duration
            val progress = buildString {
                for (i in 0 until totalBlocks)
                {
                    if ((percent * (totalBlocks - 1)).toInt() == i)
                    {
                        append("─**<:npindi:787832433398775829>**─")
                    } else
                    {
                        append("─")
                    }
                }
            }
            if (player.playingTrack == null)
            {
                event.reply("You need to be playing a track for this command to work.")
                return
            }
            if (!info.isStream)
            {
                val builder1 = EmbedBuilder()
                        .setAuthor("Now Playing:", "http://battery-productions.digital/", "https://battery-productions.digital/recordspin.gif")
                        .setTitle(info.title, info.uri)
                        .setDescription("**Duration:**\n")
                        .appendDescription(
                                progress +
                                        "\n" +
                                        "`${formatTime(track.position)}` / `${formatTime(track.duration)}` **[${"%.1f%%".format(percent * 100)}]**")
                        .setThumbnail("https://i.ytimg.com/vi/" + player.playingTrack.info.uri.replace("https://www.youtube.com/watch?v=", "") + "/maxresdefault.jpg")
                        .setColor(Color.cyan)
                event.reply(builder1.build())
                return
            }

    } catch (e: Exception) {

        }
    }
    init
    {
        name = "nowplaying"
        aliases = arrayOf("np", "now playing")
        cooldown = 3
        cooldownScope = CooldownScope.GUILD
        guildOnly = true
    }
}
private fun formatTime(timeInMillis: Long): String
{
    val hours = timeInMillis / TimeUnit.HOURS.toMillis(1)
    val minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1)
    val seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1)
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

