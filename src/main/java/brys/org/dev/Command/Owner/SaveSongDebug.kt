package brys.org.dev.Command.Owner

import brys.org.dev.Command.Music.Settings.Object.GuildSettings
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent


class SaveSongDebug: Command() {
    override fun execute(event: CommandEvent?) {
        if (event?.args?.isEmpty() == true) {
            event.reply("Song url required.")
        }
        event?.author?.idLong?.let { GuildSettings.addSongtoUserData(it, arrayOf(event.args)) }
        event?.reply("Data added!, ```${GuildSettings.findUserSongSetting(443166863996878878)}```)")
    }
    init {
        name = "-save"
        ownerCommand = true
    }
}