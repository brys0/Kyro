/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package src.Command.Queue

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.EmbedBuilder
import src.Music_Handles.Manager.PlayerManager.Companion.instance
import java.awt.Color
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*
import java.util.concurrent.TimeUnit


class Queue : Command() {
    override fun execute(event: CommandEvent?) {
        val manager = instance
        val musicm = event?.guild?.let { manager?.getGuildMusicManager(it) }
        val queue = musicm?.scheduler?.queue
        val player = musicm?.audioPlayer
        if (queue.isNullOrEmpty() && player?.playingTrack == null) {
            val noSong = EmbedBuilder()
                    .setTitle("Empty Queue")
                    .setDescription("```yaml\n")
                    .appendDescription("Looks like you have no track playing right now.\n" +
                            "To play a track just type kyro.p or kyro.play then song name of your choice and Kywo will try and find track on youtube \uD83c\uDF89")
                    .appendDescription("\n```")
                    .setFooter("Kwyo")
                    .setColor(Color.green)
            event?.reactWarning()
            event?.reply(noSong.build())
            return
        }
  try {
val trackCount = queue?.size?.let { Math.min(it, 20) }
        val tracks: List<AudioTrack> = ArrayList(queue)
        val infonp = player?.playingTrack?.info
        val thumbnail = "https://img.youtube.com/vi/" + infonp?.uri?.replace("https://www.youtube.com/watch?v=", "") + "/0.jpg"
        val infotrack = "["+infonp?.title+"]"+"("+infonp?.uri +")" + " `" + player?.playingTrack?.duration?.let { formatTime(it) } + "`\n"
        val queuebuild = EmbedBuilder()
                .setTitle("Current Queue (Total: ${queue?.size} Total Length: ${queue?.poll()?.info?.length?.let { formatTime(it) }})")
                .setThumbnail(thumbnail)
                .appendDescription(infotrack)
                .appendDescription("--------------------------------------\n")
                .appendDescription("**Coming up:**\n")
        for (i in 0 until trackCount!!) {
            val track = tracks[i]
            val info = track.info
           val queueTrackInfo = queuebuild.appendDescription(String.format(
                    "%s `%s`\n",
                    "[${info.title}](${info.uri})",
                    formatTime(info.length)
            ))
            if (!queueTrackInfo.isValidLength) {
                queueTrackInfo.toString().substring(0, 2048) + "..."
            }
            queuebuild.setFooter("Total time: ${formatTime(tracks[i].duration)}")
            queuebuild.appendDescription("--------------------------------------\n")
        }
        queuebuild.appendDescription("**End of queue**")
        event.reply(queuebuild.build())
      println(queuebuild.build())
    } catch (e: Exception) {
       if (e == IllegalArgumentException()) {
           event.reactWarning()
           event.reply("Queue is too long to send")
       } else {
           e.printStackTrace()
       }
  }
    }
    init {
        name = "queue"
        aliases = arrayOf("queue", "q", "que")
    }
}
private fun formatTime(timeInMillis: Long): String
{
    val hours = timeInMillis / TimeUnit.HOURS.toMillis(1)
    val minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1)
    val seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1)
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}