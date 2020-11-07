/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */
package src.Music_Handles.Scheduler

import com.github.ajalt.mordant.TermColors
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener
import com.sedmelluq.discord.lavaplayer.player.event.TrackStartEvent
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import jdk.jfr.Event
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.util.*
import java.util.concurrent.TimeUnit

class TrackScheduler(private val player: AudioPlayer) : AudioEventAdapter(), AudioEventListener {
    var isRepeating = false
    var lastTrack: AudioTrack? = null
    val queue: Queue<AudioTrack>


    fun queue(track: AudioTrack) {
        if (!player.startTrack(track, true)) {
            queue.offer(track)

        }

    }
    fun nextTrack() {
       if (queue.isNullOrEmpty()) {
           player.destroy()
           return
       }
        player.startTrack(queue.poll(), false)

        return
    }
    override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
        lastTrack = track


    }
    fun shuffle() {
        Collections.shuffle(queue as List<*>)
    }
    private fun formatTime(timeInMillis: Long): String {
        val hours = timeInMillis / TimeUnit.HOURS.toMillis(1)
        val minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1)
        val seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1)
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    init {
        queue = LinkedList()
    }

}
