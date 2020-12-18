package brys.org.dev.Command.Music

import brys.org.dev.Kyro.KyroUtils
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent


class Leave: Command() {
    override fun execute(event: CommandEvent?) {
       if (event?.guild?.audioManager?.isConnected == false)  {
           KyroUtils.error(event, ":x:", "I need to be in channel for you to disconnect me!")
       }
        val membervcstate = event?.member?.voiceState
        val membervcchannel = membervcstate!!.channel
        val audioManager = event.guild.audioManager
        if (membervcchannel == null) {
            KyroUtils.error(event, ":x:", "You must be in a voice channel to Disconnect the bot!")
        }
        try {
            audioManager.closeAudioConnection()
            event.reply(":outbox_tray: | Trying to disconnect from `${audioManager.connectedChannel?.name}`")
        } catch (e: Exception) {
            KyroUtils.error(event, ":x:", "Something broke `${e.message}`")
            return
        }
        event.reply(":white_check_mark: | Disconnected from `${audioManager.connectedChannel?.name}`")
    }
    init {
        name = "leave"
        aliases = arrayOf("disconnect","fuckoff")
        cooldown = 3
    }
}