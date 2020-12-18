package brys.org.dev.Command.Owner

import brys.org.dev.Command.Music.Settings.Object.GuildSettings
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser


class FindSongDebug: Command() {
    override fun execute(event: CommandEvent?) {
        if (event?.args?.isEmpty() != true) {
            event?.reply("Arguments are required.")
            return
        }
        val js = GuildSettings.findUserSongSetting(event.args.toLong())?.toJson()
        val obj: Any = JSONParser().parse(js)
        val jo: JSONObject = obj as JSONObject
        val id = jo["songUrL"]

        event.reply("Song saved for user ${event.args.toLong()}: [`$id`]")
    }
    init {
        name = "-find"
        ownerCommand = true
    }
}