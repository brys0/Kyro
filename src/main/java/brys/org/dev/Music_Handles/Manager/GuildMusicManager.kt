/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */
package brys.org.dev.Music_Handles.Manager

import brys.org.dev.Music_Handles.AudioPlayerSendHandler
import brys.org.dev.Music_Handles.Scheduler.TrackScheduler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User


class GuildMusicManager(val manager: AudioPlayerManager, guild: Guild, tx: TextChannel, req: User) {
    val audioPlayer: AudioPlayer = manager.createPlayer()
    var scheduler: TrackScheduler? = null
    val sendHandler: AudioPlayerSendHandler
    init {
        scheduler = TrackScheduler(audioPlayer, guild, tx)
        audioPlayer.addListener(this.scheduler)
        sendHandler = AudioPlayerSendHandler(this.audioPlayer)
    }
}