package src.Music_Handles

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame
import net.dv8tion.jda.api.audio.AudioSendHandler
import java.nio.Buffer
import java.nio.ByteBuffer

class AudioPlayerSendHandler(private val audioPlayer: AudioPlayer) : AudioSendHandler {
    private val buffer: ByteBuffer
    private val frame: MutableAudioFrame
    override fun canProvide(): Boolean {

        return audioPlayer.provide(frame)
    }
    override fun provide20MsAudio(): ByteBuffer? {

        (buffer as Buffer).flip()
        return buffer
    }
    override fun isOpus(): Boolean {
        return true
    }
    init {
        buffer = ByteBuffer.allocate(4096)
        frame = MutableAudioFrame()
        frame.setBuffer(buffer)
    }
}