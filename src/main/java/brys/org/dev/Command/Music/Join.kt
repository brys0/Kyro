package brys.org.dev.Command.Music

import brys.org.dev.Kyro.KyroUtils
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission


class Join: Command() {
    override fun execute(event: CommandEvent?){
        if (!event?.guild?.selfMember?.hasPermission(Permission.VOICE_CONNECT)!!) {
             KyroUtils.error(event, ":x:", "I require the permission voice connect!")
            return
        }
        val membervcstate = event.member?.voiceState
        val membervcchannel = membervcstate!!.channel
        val audioManager = event.guild.audioManager
        if (membervcchannel == null) {
            KyroUtils.error(event, ":x:", "You must be in a voice channel I can see!")
        }
        try {
            audioManager.openAudioConnection(membervcchannel)
            audioManager.isSelfDeafened = true
            event.reply(":satellite: | Trying to connect to `${membervcchannel?.name}`")
        } catch (e: Exception) {
            KyroUtils.error(event, ":x:", "Something broke `${e.message}`")
            return
        }
        event.reply(":white_check_mark: | Connected to `${membervcchannel?.name}`")
    }
    init {
        name = "join"
        aliases = arrayOf("connect","fuckon")
        cooldown = 2
    }
}