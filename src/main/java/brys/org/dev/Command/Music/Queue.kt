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
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color
import java.util.*
import java.util.concurrent.TimeUnit


class Queue : Command() {
    override fun execute(event: CommandEvent?) {
        val manager = instance
        val musicm = event?.guild?.let { manager?.getGuildMusicManager(it, event.textChannel, event.author) }
        val queue = musicm?.scheduler?.queue
        val player = musicm?.audioPlayer
        val s = musicm?.scheduler

        if (queue.isNullOrEmpty() && player?.playingTrack == null) {
            val noSong = EmbedBuilder()
                    .setTitle("Empty Queue")
                    .setDescription("```yaml\n")
                    .appendDescription("Looks like you have no track playing right now.\n" +
                            "To play a track just type -p or -play then song name of your choice and ${event?.selfUser?.name} will try and find track on youtube \uD83c\uDF89")
                    .appendDescription("\n```")
                    .setFooter("${event?.selfUser?.name}")
                    .setColor(Color.green)
            event?.reactWarning()
            event?.reply(noSong.build())
            return
        }
        fun repeating(): String {
            if (s?.isRepeating() == true) {
                return "(<:repeat:777273568387530772>) - "
            } else if (s?.isRepeating() == false) {
                return ""
            }
            return "null"
        }

  try {
val trackCount = queue?.size?.let { it.coerceAtMost(10) }
        val tracks: List<AudioTrack> = ArrayList(queue)
        val infonp = player?.playingTrack?.info
        val thumbnail = "https://img.youtube.com/vi/" + infonp?.uri?.replace("https://www.youtube.com/watch?v=", "") + "/maxresdefault.jpg"
        val infotrack = "${repeating()}["+infonp?.title+"]"+"("+infonp?.uri +")" + if (infonp?.isStream == true) " - :red_circle: LIVE\n" else " `" + player?.playingTrack?.position?.let { player.playingTrack?.duration?.minus(it) }?.let { formatTime(it) } + "`\n"
        val queuebuild = EmbedBuilder()
                .setTitle("Current Queue (Total: ${queue?.size?.plus(1)})")
                .setThumbnail(thumbnail)
                .appendDescription("**Now Playing:**\n$infotrack")
                queuebuild.appendDescription("──────────────────────────────\n")
      if (queue?.size!! > 0) {
              queuebuild.appendDescription("**Coming Up:**\n")
              queuebuild.appendDescription("──────────────────────────────\n")
      }
      var queueLength: Long = 0
        for (i in 0 until trackCount!!) {
            val track = tracks[i]
            queueLength += track.duration;
            val info = track.info

           val queueTrackInfo = queuebuild.appendDescription(String.format(
                   "%s `%s`\n",
                   "`#${i + 1}` - [${info.title}](${info.uri})",
                   formatTime(info.length),
           ))
            if (!queueTrackInfo.isValidLength) {
                queueTrackInfo.toString().substring(0, 2048) + "..."
            }
            val total = queueLength + player?.playingTrack?.position!!
            queuebuild.setFooter("Requested by: ${event.author.asTag}. Showing ${trackCount}. Of total ${queue.size}. Song Time: ${if (infonp?.isStream == true) "LIVE" else formatTime(total) }")
        }
        event.reply(queuebuild.build())
    } catch (e: Exception) {
      println(e.message)
      event.reply("Something broke")
       }

    }

    init {
        name = "queue"
        aliases = arrayOf("queue", "q", "que")
        cooldown = 2
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