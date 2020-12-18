package brys.org.dev.Command.Music

import brys.org.dev.Music_Handles.Manager.PlayerManager
import brys.org.dev.lib.Util.AudioUtil.BassBooster
import brys.org.dev.lib.Util.IsInt
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent


class BassBoost: Command() {
    override fun execute(event: CommandEvent)
    {
        val playerManager = PlayerManager.instance
        val GMM = playerManager!!.getGuildMusicManager(event.guild, event.textChannel, event.author)
        val s = GMM.scheduler
        val player = GMM.audioPlayer

        if (!IsInt(event.args)) {
            event.reactError()
            event.reply("You need to have a number e.x `100`")
            return
        }
        if (event.args.toInt() < 0 || event.args.toInt() > 200) {
            event.reactError()
            event.reply("You need to have a boost of `0-200`")
            return
        }
        val boost = event.args?.toFloatOrNull()
        if (boost != null) {
            BassBooster(player).boost(boost)
        }
event.reply("Bassboost now `${event.args.toInt()}%`")
    }
    init {
        name = "bassboost"
        aliases = arrayOf("bb","bass")
        guildOnly = true
    }
}