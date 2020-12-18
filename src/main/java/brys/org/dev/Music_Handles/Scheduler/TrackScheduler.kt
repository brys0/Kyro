/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */
package brys.org.dev.Music_Handles.Scheduler

import com.github.ajalt.mordant.TermColors
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import java.awt.Color
import java.lang.Exception
import java.lang.NullPointerException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.sound.midi.Track

class TrackScheduler(private val player: AudioPlayer, guild: Guild, tx: TextChannel, req: User) : AudioEventAdapter(), AudioEventListener {
    val req: User = req
    val tx: TextChannel = tx
    val guild: Guild = guild
    private var repeating = false
    var lastTrack: AudioTrack? = null
    val queue: Queue<AudioTrack>
    var current: AudioTrack? = null
var queueRepeat = false
    fun queue(track: AudioTrack, channel: TextChannel) {
        try {
            if (!player.startTrack(track, true)) {
                queue.offer(track)
            }
        } catch (e: FriendlyException) {
            channel.sendMessage("An error occurred most likely this is because the requested song is age restricted.")
        }
    }

    override fun onTrackException(player: AudioPlayer, track: AudioTrack, exception: FriendlyException) {
        tx.sendMessage("The track encountred a exception. (${exception.message})").queue()
    }

    override fun onTrackStuck(player: AudioPlayer, track: AudioTrack, thresholdMs: Long) {
        tx.sendMessage("Track got stuck at `${formatTime(thresholdMs)}`").queue()
        nextTrack()
    }

    fun nextTrack() {
        player.startTrack(queue.poll(), false)
    }

    override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
        this.lastTrack = track;
        if (endReason.mayStartNext) {
            if (repeating)
                player.startTrack(lastTrack!!.makeClone(), false);
            else
                nextTrack();
        }
    }

    override fun onTrackStart(player: AudioPlayer?, track: AudioTrack?) {
        println(TermColors().blue("────────────────────Track────────────────────"))
        println(" ${TermColors().brightMagenta}Track ${track?.info?.title}, now playing in ${guild.name}")
        println(TermColors().blue("─────────────────────────────────────────────"))
        try {
        player?.isPaused = false
        if (current == null || current!!.identifier != track?.identifier) {
            current = track
            val durString = if (track?.info?.isStream == true) "LIVE" else track?.info?.length?.let { formatTime(it) }
            val queueOrNot = if (queue.peek() == null) "None" else "[${queue.peek().info.title}](${queue.peek().info.uri})"
            val NowPlayingEmb = EmbedBuilder()
                .setAuthor("Now Playing")
                .setTitle(track?.info?.title,track?.info?.uri)
                .setColor(Color.decode("#8293EE"))
                .addField("Duration",durString,true)
                .addField("Channel", track?.info?.author, true)
                .addField("Next Up",queueOrNot,true)
                .setThumbnail("https://i.ytimg.com/vi/" + track?.info?.uri?.replace("https://www.youtube.com/watch?v=", "") + "/maxresdefault.jpg")
                .setFooter("Requested By: ${req.asTag}", req.effectiveAvatarUrl)
                .build()
            tx.sendMessage(NowPlayingEmb).queue()
        }
    } catch (e: NullPointerException) {
        tx.sendMessage("A error occured when getting the now playing event for this track.").queue()

        }    }
    @JvmName("setRepeating1")
    fun setRepeating(repeating: Boolean) {
        this.repeating = repeating
    }

    fun isRepeating(): Boolean {
        return repeating
    }

    fun shuffle() {
        Collections.shuffle(queue as List<*>)
    }

    fun remove(i: Int): AudioTrack? {
        val list = queue.toMutableList()
        val removed = list.removeAt(i - 1)
        queue.clear()
        queue.addAll(list)
        return removed
    }

    fun skip(number: Int) {
        println("a")
        for (i in 0 until number) removeT(0)
    }
    fun queueAsLinked(): MutableList<AudioTrack> {
        val list = queue.toMutableList()

        return list
    }
    fun moveTrack(start: Int, final: Int): AudioTrack {
        val queuem = queue.toMutableList()
        val tmp = queuem.removeAt(start)
        queue.clear()
        val h = queuem.add(final, tmp)
        return tmp
    }
    private fun formatTime(timeInMillis: Long): String {
        val hours = timeInMillis / TimeUnit.HOURS.toMillis(1)
        val minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1)
        val seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1)
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
    fun removeT(index: Int): AudioTrack? {
       val list =  queueAsLinked()
       val remove = list.removeAt(index)
        queue.clear()
        queue.addAll(list)
        return remove
}
    fun loop() {
        if (current != null) {
            if (repeating){
                val fullrepeat = current!!.makeClone()
                queue.offer(fullrepeat)
            }
        }
    }
fun requester(): User {
    return req
}
    init {
        queue = LinkedList()
    }

}
