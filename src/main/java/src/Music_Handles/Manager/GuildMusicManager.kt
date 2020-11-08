/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */
package src.Music_Handles.Manager

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import src.Music_Handles.AudioPlayerSendHandler
import src.Music_Handles.Scheduler.TrackScheduler


class GuildMusicManager(manager: AudioPlayerManager) {
    val audioPlayer: AudioPlayer
    val scheduler: TrackScheduler
    val sendHandler: AudioPlayerSendHandler

    init {
        audioPlayer = manager.createPlayer()
        scheduler = TrackScheduler(audioPlayer)
        audioPlayer.addListener(scheduler)
        sendHandler = AudioPlayerSendHandler(audioPlayer)
    }
}