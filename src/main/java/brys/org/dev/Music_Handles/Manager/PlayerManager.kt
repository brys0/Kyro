/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package brys.org.dev.Music_Handles.Manager


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
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import java.awt.Color
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

class PlayerManager private constructor() {
    @Suppress
    val t = TermColors()
     val playerManager: AudioPlayerManager
    private val musicManagers: MutableMap<Long, GuildMusicManager>

    @Synchronized
    fun getGuildMusicManager(guild: Guild, channel: TextChannel, req: User): GuildMusicManager {
        val guildId = guild.idLong
        var musicManager = musicManagers[guildId]
        if (musicManager == null) {
            musicManager = GuildMusicManager(playerManager, guild, channel, req)
            musicManagers[guildId] = musicManager
        }
        guild.audioManager.sendingHandler = musicManager.sendHandler
        return musicManager
    }

    fun loadAndPlay(channel: TextChannel, trackUrl: String, req: User) {
        val timebefore = System.currentTimeMillis()
        val musicManager = getGuildMusicManager(channel.guild, channel, req)
        val player = musicManager.audioPlayer
        playerManager.loadItemOrdered(musicManager, trackUrl, object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack) {
                play(musicManager, track, channel)
                if (musicManager.scheduler?.queue?.peek() == null) {
                    return
                } else {
                    val builder = EmbedBuilder()
                        .setTitle("Added Track")
                        .addField("Track", "[${track.info.title}](${track.info.uri})", true)
                        .addField("Channel", track.info.author, true)
                        .setThumbnail(
                            "https://i.ytimg.com/vi/" + trackUrl.replace(
                                "https://www.youtube.com/watch?v=",
                                ""
                            )+"/maxresdefault.jpg")
                        .addField(
                            "Duration", (if (track.info.isStream) {
                                "\uD83D\uDD34 - Live Stream"
                            } else {
                                formatTime(track.duration)
                            }).toString(), true
                        )
                        .setFooter("Total time taken to load track: ${System.currentTimeMillis() - timebefore}ms")
                        .setColor(Color.cyan)
                        .setFooter("Requested By: ${req.asTag}", req.effectiveAvatarUrl)
                    channel.sendMessage(builder.build()).queue()
                    println(t.magenta("Track ${track.info.title} now playing in guild: ${t.red}${channel.guild.name}${t.reset}"))
                }
            }
                    override fun playlistLoaded(playlist: AudioPlaylist) {
                var firstTrack = playlist.selectedTrack
                if (firstTrack == null) {
                    firstTrack = playlist.tracks.removeAt(0)
                }
                channel.sendMessage("Adding playlist: `" + playlist.name + "`").queue()
                        val playlistCount = playlist.tracks.size.coerceAtMost(10)
                        val tracks: List<AudioTrack> = ArrayList(playlist.tracks)
                        val builder1 = EmbedBuilder()
                            .setTitle(firstTrack!!.info.title, firstTrack.info.uri)
                            .setDescription("Showing first **${playlistCount}** Tracks.\n")
                            .setThumbnail("https://i.ytimg.com/vi/" + firstTrack.info.uri.replace("https://www.youtube.com/watch?v=", "") + "/maxresdefault.jpg")
                            .setColor(Color.BLUE)
                        for (i in 0 until playlistCount) {
                            val track = tracks[i]
                            val info = track.info;
                           builder1.appendDescription(
                                String.format(
                                    "%s `%s`\n",
                                    "`#${i + 1}` - [${info.title}](${info.uri})",
                                    formatTime(info.length)
                                )
                            )
                        }
                        channel.sendMessage(builder1.build()).queue()
                play(musicManager, firstTrack, channel)
                playlist.tracks.forEach(Consumer { track: AudioTrack? ->
                    if (track != null) {
                        musicManager.scheduler?.queue(track, channel)
                    }
                })
            }

            override fun noMatches() {
                channel.sendMessage("Nothing found perhaps this url is hidden $trackUrl").queue()

            }
            override fun loadFailed(exception: FriendlyException) {
                channel.sendMessage("Couldn't play the track. (`${exception.message}`)").queue()
            }
        })

    }

    fun play(musicManager: GuildMusicManager, track: AudioTrack, channel: TextChannel) {
        if (track != null) {
                musicManager.scheduler?.queue(track, channel)
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