/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package src.Music_Handles.Manager

import com.github.ajalt.mordant.TermColors
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import java.awt.Color
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

class PlayerManager private constructor() {
    val t = TermColors()
    private val playerManager: AudioPlayerManager
    private val musicManagers: MutableMap<Long, GuildMusicManager>
    @Synchronized
    fun getGuildMusicManager(guild: Guild): GuildMusicManager {
        val guildId = guild.idLong
        var musicManager = musicManagers[guildId]
        if (musicManager == null) {
            musicManager = GuildMusicManager(playerManager)
            musicManagers[guildId] = musicManager
        }
        guild.audioManager.sendingHandler = musicManager.sendHandler
        return musicManager
    }

    fun loadAndPlay(channel: TextChannel, trackUrl: String) {
        val timebefore = System.currentTimeMillis()
        val musicManager = getGuildMusicManager(channel.guild)
        playerManager.loadItemOrdered(musicManager, trackUrl, object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack) {
                play(musicManager, track, channel)

                val builder = EmbedBuilder()
                        .setTitle("Added Song:")
                        .addField("Title:", track.info.title, true)
                        .addField("Url:", track.info.uri, true)
                        .addField("Channel:", track.info.author, true)
                        .setThumbnail("https://img.youtube.com/vi/" + track.info.uri.replace("https://www.youtube.com/watch?v=", "") + "/0.jpg")
                        .addField("Duration", formatTime(track.info.length), true)
                        .setFooter("Total time taken to load track: ${System.currentTimeMillis() - timebefore}ms")
                        .setColor(Color.cyan)
                channel.sendMessage(builder.build()).queue()
                println(t.magenta("Track ${track.info.title} now playing in guild: ${t.red}${channel.guild.name}${t.reset}"))
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {
                var firstTrack = playlist.selectedTrack
                if (firstTrack == null) {
                    firstTrack = playlist.tracks.removeAt(0)
                }
                channel.sendMessage("Adding playlist: `" + playlist.name + "`").queue()
                val builder1 = EmbedBuilder()
                        .setTitle(firstTrack!!.info.title, firstTrack.info.uri)
                        .setImage("https://img.youtube.com/vi/" + trackUrl.replace("https://www.youtube.com/watch?v=", "") + "/0.jpg")
                        .setColor(Color.BLUE)
                channel.sendMessage(builder1.build()).queue()
                play(musicManager, firstTrack, channel)
                playlist.tracks.forEach(Consumer { track: AudioTrack? ->
                    if (track != null) {
                        musicManager.scheduler.queue(track)
                    }
                })
            }

            override fun noMatches() {
                channel.sendMessage("Nothing found perhaps this url is hidden $trackUrl").queue()

            }

            override fun loadFailed(exception: FriendlyException) {
                channel.sendMessage("Couldn't play the track.").queue()
            }
        })
    }

    private fun play(musicManager: GuildMusicManager, track: AudioTrack?, channel: TextChannel) {

        if (track != null) {
            musicManager.scheduler.queue(track)
            channel.sendMessage("Now playing: ${track.info.title}").queue()
        }
    }

    private fun formatTime(timeInMillis: Long): String {
        val hours = timeInMillis / TimeUnit.HOURS.toMillis(1)
        val minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1)
        val seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1)
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    companion object {
        private var INSTANCE: PlayerManager? = null

        @JvmStatic
        @get:Synchronized
        val instance: PlayerManager?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = PlayerManager()
                }
                return INSTANCE
            }
    }

    init {
        musicManagers = HashMap()
        playerManager = DefaultAudioPlayerManager()
        AudioSourceManagers.registerRemoteSources(playerManager)
        AudioSourceManagers.registerLocalSource(playerManager)
        playerManager.registerSourceManager(TwitchStreamAudioSourceManager())
    }
}