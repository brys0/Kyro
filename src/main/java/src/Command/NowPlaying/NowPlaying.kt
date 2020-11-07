/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package src.Command.NowPlaying

import com.github.ajalt.mordant.TermColors
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder
import src.Music_Handles.Manager.PlayerManager.Companion.instance
import java.awt.Color
import java.lang.Exception
import java.util.concurrent.TimeUnit

class NowPlaying : Command()
{
    override fun execute(event: CommandEvent)
    {

        val t = TermColors()
        val playerManager = instance
        val musicManager = playerManager!!.getGuildMusicManager(event.guild)
        val player = musicManager.audioPlayer


        try
        {


        } catch (e: Exception) {
            event.reply("No track is playing right now. Try again with a playing track.\n" +
                    "Don't know how to play your favorite track? Just do `kyro.play {track_name}`")
            println(t.magenta("Player failed to find playing track in: "  + event.guild.name + " " + event.guild.id))
          println(t.red("Error: ${e.message}"))
            return
        }
        try {
        val info = player.playingTrack.info
        val track = player.playingTrack
            val totalBlocks = 20
            val percent = track.position.toDouble() / track.duration
            val progress = buildString {
                for (i in 0 until totalBlocks)
                {
                    if ((percent * (totalBlocks - 1)).toInt() == i)
                    {
                        append("__**\uD83D\uDD34**__")
                    } else
                    {
                        append("\u2015")
                    }
                }
                append("**%.1f**%%".format(percent * 100))
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
                                        "`${formatTime(track.duration - track.position)}` / `${formatTime(track.duration)}`")
                        .setThumbnail("https://img.youtube.com/vi/" + info.uri.replace("https://www.youtube.com/watch?v=", "") + "/0.jpg")
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
    }
}
private fun formatTime(timeInMillis: Long): String
{
    val hours = timeInMillis / TimeUnit.HOURS.toMillis(1)
    val minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1)
    val seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1)
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}