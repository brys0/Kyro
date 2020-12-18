package brys.org.dev.Command.Music.Settings

import brys.org.dev.Command.Music.Settings.Object.GuildSettings
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Role

class Settings : Command() {
    override fun execute(event: CommandEvent?) {
        if (event?.args?.isEmpty() == true) {
            val SettingsEmbed = EmbedBuilder()
                .setTitle("${event.jda?.selfUser?.name} Guild settings.")
                .setDescription("Your guild settings including this guilds set DJ role")
                .addField("DJ Role", "`[PLACE_HOLDER_STRING]`", false)
                .addField("DJ Only", "`[PLACE_HOLDER_BOOLEAN]`", false)
                .addField("Prefix", "`[PLACE_HOLDER_STRING]`", false)
                .addField("Music Channel", "`[PLACE_HOLDER_STRING]`", false)
                .build()
            event.reply(SettingsEmbed)
            return
        }
        if (event?.args?.contains("dj") == true) {
GuildSettings.addGuildDJ(event.guild, event.guild.getRolesByName(event.args.removePrefix("dj"), true)[0])
        }
     }
    init {
        name = "settings"
        aliases = arrayOf("setting", "set")
        cooldownScope = CooldownScope.GUILD
        cooldown = 5
    }
}