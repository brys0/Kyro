package brys.org.dev.Command.Music

import brys.org.dev.Kyro.KyroUtils
import brys.org.dev.Music_Handles.Manager.PlayerManager
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Role


class LoopQueue: Command() {
    override fun execute(event: CommandEvent?) {
        val playerManager = PlayerManager.instance
        val GMM = event?.guild?.let { playerManager!!.getGuildMusicManager(it, event.textChannel, event.author) }
        val s = GMM?.scheduler
        val player = GMM?.audioPlayer

        if (player?.playingTrack == null) {
            if (event != null) {
                KyroUtils.error(event,"<:KyroX:778465200902635560>","There must be a playing track! Don't know how to play one type `-h` for more help.")
            }
        }
        if (event?.member?.roles?.stream()?.noneMatch { r: Role -> r.name.equals("DJ", ignoreCase = true) or !event.isOwner or !event.member.hasPermission(Permission.ADMINISTRATOR)}!!) {
s?.queueRepeat = true
        }
    }
}